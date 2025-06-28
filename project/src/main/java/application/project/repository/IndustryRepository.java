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

import application.project.domain.Industry;
import application.project.domain.dto.request.ReqIndustry;
import application.project.util.SecurityUtil.SecurityUtil;

@Repository
public class IndustryRepository {
    private final NamedParameterJdbcTemplate jdbc;

    public IndustryRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Optional<Industry> findById(int id) {
        String sql = "SELECT * FROM industries WHERE idt_id = :id";
        try {
            Industry industry = jdbc.queryForObject(
                    sql,
                    new MapSqlParameterSource("id", id),
                    new BeanPropertyRowMapper<>(Industry.class));
            return Optional.of(industry);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Industry> findByName(String name) {
        String sql = "SELECT * FROM industries WHERE idt_name = :name";
        try {
            Industry industry = jdbc.queryForObject(
                    sql,
                    new MapSqlParameterSource("name", name),
                    new BeanPropertyRowMapper<>(Industry.class));
            return Optional.of(industry);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public int update(int id, ReqIndustry industry) {
        String sql = "UPDATE industries SET idt_name = :idt_name, idt_updated_by = :idt_updated_by WHERE idt_id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("idt_name", industry.getIdt_name())
                .addValue("idt_updated_by", Long.valueOf(SecurityUtil.getCurrentUserLogin().get()))
                .addValue("id", id);
        return jdbc.update(sql, params);
    }

    public Optional<Integer> create(ReqIndustry industry) {
        String sql = "UPDATE industries SET idt_name = :idt_name, idt_updated_by = :idt_updated_by, idt_created_by = :idt_created_by WHERE idt_id = :id";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("idt_name", industry.getIdt_name())
                .addValue("idt_created_by", Long.valueOf(SecurityUtil.getCurrentUserLogin().get()))
                .addValue("idt_updated_by", Long.valueOf(SecurityUtil.getCurrentUserLogin().get()));
                
    this.jdbc.update(sql, params, keyHolder, new String[] { "idt_id" });
    Number key = keyHolder.getKey();

        return key != null
                ? Optional.of(key.intValue())
                : Optional.empty();
}


    public int delete(int id) {
        String sql = "UPDATE industries SET idt_deleted = true WHERE idt_id = :id";
        return jdbc.update(sql, new MapSqlParameterSource("id", id));
    }
}
