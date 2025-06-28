package application.project.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import application.project.domain.JobPost;

@Repository
public class JobPostRepository {
    private final NamedParameterJdbcTemplate jdbc;

    public JobPostRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Optional<Long> create( Map<String, Object> ColumnsAndValues) {

        @SuppressWarnings("unchecked")
        List<String> columns = (List<String>) ColumnsAndValues.get("columns");
        List<String> cols_with_colon = columns.stream().map(col -> ":" + col).toList();

        @SuppressWarnings("unchecked")
        Map<String, Object> values = (Map<String, Object>) ColumnsAndValues.get("values");

        String insert_query = "INSERT INTO jobPosts ("  +  String.join(", " , columns) + ") VALUES (" + String.join(", ", cols_with_colon) + " )";
               
        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        values.forEach((key, value) -> params.addValue(key, value));

        this.jdbc.update(insert_query, params, keyHolder, new String[] { "jp_id" });
        Number key = keyHolder.getKey();

        return key != null
                ? Optional.of(key.longValue())
                : Optional.empty();
    }

    public Optional<JobPost> findById(long id) {
        String find_query = "SELECT * FROM jobPosts where jp_id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        try {
            JobPost jp = this.jdbc.queryForObject(
                    find_query,
                    params,
                    new BeanPropertyRowMapper<>(JobPost.class));
            return Optional.of(jp);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @SuppressWarnings("unchecked")
    public int update(long id, Map<String, Object> conditionsAndValue) {
        List<String> conditions =  (List<String>)conditionsAndValue.get("conditions");
        Map<String, Object> values = (Map<String, Object>) conditionsAndValue.get("values");

        String update_query = " UPDATE skills SET " + String.join(", " ,conditions) + "WHERE jp_id = :id ";
                
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("jp_id", id);
        values.forEach((key, value) -> params.addValue(key, value));
        

        int row = this.jdbc.update(update_query, params);

        return row;
    }

    public int delete(long id) {
        String query = "UPDATE jobPosts SET jp_deleted = :jp_deleted WHERE jp_id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("jp_deleted", true);

        int row = this.jdbc.update(query, params);
        return row;
    }
}
