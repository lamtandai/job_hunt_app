package application.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import application.project.domain.Company.Company;
import application.project.domain.DTO.CompanyDTO;

@Repository
public class CompanyRepository extends AuditRepository {
    private final NamedParameterJdbcTemplate jdbc;

    public CompanyRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Optional<Integer> create(CompanyDTO companyDTO) {
        String insert_query = """
                INSERT INTO companies
                (company_name, industry_id, description, created_by_user_id, updated_by_user_id)
                VALUES (:company_name, :industry_id, :description, :created_by_user_id, :updated_by_user_id)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource()

                .addValue("company_name", companyDTO.getCompany_name())
                .addValue("industry_id", companyDTO.getIndustry_id())
                .addValue("description", companyDTO.getDescription())
                .addValue("created_by_user_id", auditUtil.currentUserId())
                .addValue("updated_by_user_id", auditUtil.currentUserId());

        this.jdbc.update(insert_query, params, keyHolder, new String[] { "company_id" });
        Number key = keyHolder.getKey();

        return key != null
                ? Optional.of(key.intValue())
                : Optional.empty();
    }

    public Optional<Company> findOne(int id) {
        String find_query = "SELECT * FROM companies WHERE company_id = :id";
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

    public Optional<List<Company>> findAll() {
        String findAll_query = "SELECT * FROM companies";
        return Optional.ofNullable(this.jdbc.query(
                findAll_query,
                new BeanPropertyRowMapper<>(Company.class)));
    }

    public Optional<Company> update(Company company) {
        String sql = """
      UPDATE companies
         SET
           company_name        = :company_name,
           description         = :description,
           industry_id         = :industry_id,
           numberOfFollower      = :numberOfFollower,
           size                = :size,
           logo                = :logo,
           location            = :location,
           website_url         = :website_url,
           verified            = :verified,
           updated_by_user_id  = :updated_by_user_id
       WHERE company_id = :company_id
    """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("company_id", company.getCompany_id())
                .addValue("description", company.getDescription())
                .addValue("industry_id", company.getIndustry_id())
                .addValue("company_name", company.getCompany_name())
                .addValue("numberOfFollower", company.getNumberOfFollower())
                .addValue("size", company.getSize())
                .addValue("logo", company.getLogo())
                .addValue("location", company.getLocation())
                .addValue("verified", company.isVerified())
                .addValue("website_url", company.getWebsite_url())
                .addValue("updated_by_user_id", auditUtil.currentUserId());

        int row_updated = this.jdbc.update(sql, params);

        return row_updated != 0 ? findOne(company.getCompany_id()) : Optional.empty();
    }
}
