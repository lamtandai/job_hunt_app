package application.project.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import application.project.domain.Company;
import application.project.domain.dto.request.ReqCompanyDTO;

@Repository
public class CompanyRepository {
    private final NamedParameterJdbcTemplate jdbc;

    private final short Page_num_minimum = 1;
    private final short Limit_minimum = 10;

    public CompanyRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Optional<Integer> create(ReqCompanyDTO companyDTO) {
        String insert_query = """
                INSERT INTO companies
                (cpn_name, cpn_description, cpn_location, cpn_created_by, cpn_updated_by)
                VALUES (:cpn_name, :cpn_description, :cpn_location, :cpn_created_by, :cpn_updated_by)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("cpn_name", companyDTO.getCompany_name())
                .addValue("cpn_description", companyDTO.getDescription())
                .addValue("cpn_location", companyDTO.getLocation())
                .addValue("cpn_created_by", companyDTO.getCreated_by())
                .addValue("cpn_updated_by", companyDTO.getUpdated_by());

        this.jdbc.update(insert_query, params, keyHolder, new String[] { "cpn_id" });
        Number key = keyHolder.getKey();

        return key != null
                ? Optional.of(key.intValue())
                : Optional.empty();
    }

    public Optional<Company> findOne(int id) {
        String find_query = "SELECT * FROM companies WHERE cpn_id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        try {
            Company company = this.jdbc.queryForObject(
                    find_query,
                    params,
                    new BeanPropertyRowMapper<>(Company.class));
            return Optional.of(company);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    public Optional<Company> update(int company_id, List<String> conditions, Map<String, Object> value_for_update) {
        String sql = "UPDATE companies SET " + String.join(", ", conditions) + " WHERE cpn_id = :cpn_id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("cpn_id", company_id);
        value_for_update.forEach((key, value) -> {
            params.addValue(key, value);
        });

        // Optionally handle cpn_refresh_token and cpn_is_deleted if present in
        // value_for_update
        
        if (value_for_update.containsKey("cpn_deleted")) {
            params.addValue("cpn_deleted", value_for_update.get("cpn_is_deleted"));
        }

        int row_updated = this.jdbc.update(sql, params);

        return row_updated != 0 ? findOne(company_id) : Optional.empty();
    }

    public Page<Company> findAllByPage(int page, int size) {
        String querySql = "SELECT * FROM companies LIMIT :limit OFFSET :offset";

        int effectivePage = Math.max(Page_num_minimum, page);
        int effectiveLimit = Math.max(Limit_minimum, size);

        Pageable pg = PageRequest.of(effectivePage - 1, effectiveLimit);

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("limit", effectiveLimit)
                .addValue("offset", pg.getOffset());

        List<Company> companies = this.jdbc.query(
                querySql,
                params,
                new BeanPropertyRowMapper<>(Company.class));

        // Get total count for pagination
        String countSql = "SELECT COUNT(*) FROM companies";
        int total = this.jdbc.queryForObject(countSql, new MapSqlParameterSource(), Integer.class);

        return new PageImpl<>(companies, pg, total);
    }
    
    public void delete(int cpn_id){
        String delete_query = "UPDATE companies SET cpn_deleted = :cpn_deleted WHERE cpn_id = :cpn_id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("cpn_id", cpn_id)
                .addValue("cpn_deleted", true);

        this.jdbc.update(delete_query, params);
    }
}
