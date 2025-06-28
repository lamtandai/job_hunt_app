package application.project.repository;

import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import application.project.domain.Skill;
import application.project.domain.dto.request.ReqSkill;
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

}
