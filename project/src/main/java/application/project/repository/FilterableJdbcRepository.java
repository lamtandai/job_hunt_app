package application.project.repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import application.project.repository.JdbcSpecification.IjdbcSpecification;

@Repository
public class FilterableJdbcRepository {
    private final NamedParameterJdbcTemplate jdbc;

    public FilterableJdbcRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public <T> Page<T> findAll(
            IjdbcSpecification<T> spec,
            Pageable pageable) {

        String base = "SELECT * FROM " + spec.getTableName();
        String where = spec.toSqlClause();
        String sql = base + (where.isBlank() ? "" : " WHERE " + where);

        if (pageable.getSort().isSorted()) {
            String orderBy = pageable.getSort()
                    .stream()
                    .map(order -> order.getProperty() + " " + order.getDirection().name())
                    .collect(Collectors.joining(", "));

            sql += " ORDER BY " + orderBy;
        }

        sql += " LIMIT :limit OFFSET :offset";
        Map<String, Object> params = spec.getParameters();
        params.put("limit", pageable.getPageSize());
        params.put("offset", pageable.getOffset());

        List<T> list = this.jdbc.query(
                sql,
                params,
                new BeanPropertyRowMapper<>(spec.getEntityClass()));

        String countSql = "SELECT COUNT(*) FROM " + spec.getTableName() + (where.isBlank() ? "" : " WHERE " + where);
        int total = this.jdbc.queryForObject(countSql, params, Integer.class);

        return new PageImpl<>(list, pageable, total);
    }
}
