package application.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JobPostSkillRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JobPostSkillRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addMultipleSkills(long jobPostId, List<String> skillIds) {
        String sql = "INSERT INTO skills_for_jobPosts (jp_id, sk_id) VALUES (:jp_id, :sk_id)";
        List<MapSqlParameterSource> batchParams = skillIds.stream()
            .map(skillId -> new MapSqlParameterSource()
                .addValue("jp_id", jobPostId)
                .addValue("sk_id", skillId))
            .toList();
        jdbcTemplate.batchUpdate(sql, batchParams.toArray(new MapSqlParameterSource[0]));
    }

    public Optional<List<String>> fetchJobPostSkills(long jp_id){
        String sql = """
            Select sk_name FROM skills A 
            JOIN skills_for_jobPosts B 
            ON A.sk_id = B.sk_id
            WHERE B.jp_id = :jp_id;        
        """;
        MapSqlParameterSource param = new MapSqlParameterSource("jp_id", jp_id);
        List<String> skillIds = jdbcTemplate.queryForList(sql, param, String.class);
        return skillIds.isEmpty() ? Optional.empty() : Optional.of(skillIds);
    }

    public int deleteSkillsOfJobPostBySkillId(int skillId){
        String sql = "DELETE FROM skills_for_jobPosts WHERE sk_id = :sk_id";
        MapSqlParameterSource param = new MapSqlParameterSource("sk_id", skillId);
        return jdbcTemplate.update(sql, param);
    }
    public int deleteSkillsOfJobPostByJobPostId(long jp_id){
        String sql = "DELETE FROM skills_for_jobPosts WHERE jp_id = :jp_id";
        MapSqlParameterSource param = new MapSqlParameterSource("jp_id", jp_id);
        return jdbcTemplate.update(sql, param);
    }
}
