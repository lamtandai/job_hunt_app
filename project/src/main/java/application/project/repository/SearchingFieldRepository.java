package application.project.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import application.project.domain.SearchingField;

@Repository
public class SearchingFieldRepository {
    private final JdbcTemplate jdbcTemplate;

    public SearchingFieldRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List <SearchingField> getSearchList(String table_name){
        String query = "SELECT field_name FROM search_fields where searchable = true AND table_name = ?";
        return this.jdbcTemplate.query(
            query,
            (rs, rowNum) -> new SearchingField(rs.getString("field_name")),
            new Object[]{table_name}
            );
    }
    
    
}
