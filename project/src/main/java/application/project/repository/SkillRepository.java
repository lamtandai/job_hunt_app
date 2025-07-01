package application.project.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import application.project.domain.Skill;
import application.project.domain.dto.request.ReqSkill;
import application.project.repository.JdbcSpecification.IjdbcSpecification;
import application.project.util.SecurityUtil.SecurityUtil;

@Repository
public class SkillRepository {
    private final NamedParameterJdbcTemplate jdbc;

    public SkillRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Optional<Integer> create(ReqSkill skill) {
        String insert_query = "INSERT INTO skills (sk_name, sk_created_by,sk_updated_by) VALUES (:sk_name, :sk_created_by, :sk_updated_by)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("sk_name", skill.getSk_name())
                .addValue("sk_created_by", Long.valueOf(SecurityUtil.getCurrentUserLogin().get()))
                .addValue("sk_updated_by", Long.valueOf(SecurityUtil.getCurrentUserLogin().get()));

        this.jdbc.update(insert_query, params, keyHolder, new String[] { "sk_id" });
        Number key = keyHolder.getKey();

        return key != null
                ? Optional.of(key.intValue())
                : Optional.empty();
    }

    public Optional<Skill> findById(int id) {
        String find_query = "SELECT * FROM skills where sk_id = :id AND sk_deleted = :sk_deleted";
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("id", id)
            .addValue("sk_deleted", false);

        try {
            Skill skill = this.jdbc.queryForObject(
                    find_query,
                    params,
                    new BeanPropertyRowMapper<>(Skill.class));
            return Optional.of(skill);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }
    public Optional<Skill> findByName(String name) {
        String find_query = "SELECT * FROM skills where sk_name = :sk_name";
        MapSqlParameterSource params = new MapSqlParameterSource("sk_name", name);

        try {
            Skill skill = this.jdbc.queryForObject(
                    find_query,
                    params,
                    new BeanPropertyRowMapper<>(Skill.class));
            return Optional.of(skill);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }


    public int update(ReqSkill skill, int id) {
        String update_query = "UPDATE skills SET sk_name = :sk_name, sk_updated_by = :sk_updated_by WHERE sk_id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("sk_name", skill.getSk_name())
                .addValue("sk_updated_by", Long.valueOf(SecurityUtil.getCurrentUserLogin().get()));

        int row_updated = this.jdbc.update(update_query, params);

        return row_updated;
    }

    public int delete(int id) {
        String query = "UPDATE skills SET sk_deleted = :sk_deleted WHERE sk_id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("sk_deleted", true);

        int row = this.jdbc.update(query, params);
        return row;
    }

    public  <T>Page<T> findAll(
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

    public List<Integer> checkSkillsExist(List<String>skills){
        if (skills == null || skills.isEmpty()){
            return List.of();
        }   
        MapSqlParameterSource params = new MapSqlParameterSource();
        StringBuilder derivedTable = new StringBuilder();
        for (int i = 0; i < skills.size(); i++){
            derivedTable.append("SELECT :").append(skills.get(i)).append(" AS sk_id");
            if (i < skills.size() - 1){
                derivedTable.append(" UNION ALL ");
            }
        }

        skills.forEach(skill -> params.addValue(skill, skill));

        String query = "SELECT v.sk_id FROM ( " + derivedTable + " ) AS v" +
                    " Left JOIN skills s ON v.sk_id = s.sk_id"+
                    " WHERE s.sk_id is NULL" ;
        
        return jdbc.queryForList(query, params, Integer.class);
    }
    
}
