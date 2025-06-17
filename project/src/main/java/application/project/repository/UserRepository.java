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

import application.project.domain.DTO.UserDTO.UserRegisterDTO;
import application.project.domain.Enumeration.UserRole.UserRole;
import application.project.domain.User.User_account;

@Repository
public class UserRepository {
    private final NamedParameterJdbcTemplate jdbc;
    private final short Page_num_minimum = 1;
    private final short Limit_minimum = 10;

    public UserRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;

    }

    public Optional<Long> createUser(UserRegisterDTO newUser) {
        String insert_query = "INSERT INTO user_accounts (username, password, email, user_role) VALUES (:username, :password, :email, :user_role)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("username", newUser.getUsername())
                .addValue("password", newUser.getPassword())
                .addValue("email", newUser.getEmail())
                .addValue("user_role", UserRole.Job_seeker.name());

        this.jdbc.update(insert_query, params, keyHolder, new String[] { "user_account_id" });

        Number key = keyHolder.getKey();

        return key != null
                ? Optional.of(key.longValue())
                : Optional.empty();

    }

    public Page<User_account> findAllByPage(int page, int size) {
        String querySql = "SELECT * FROM user_accounts LIMIT :limit OFFSET :offset";

        int effectivePage = Math.max(Page_num_minimum, page);
        int effectiveLimit = Math.max(Limit_minimum, size);

        Pageable pg = PageRequest.of(effectivePage - 1, effectiveLimit);

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("limit", effectiveLimit)
                .addValue("offset", pg.getOffset());

        List<User_account> users = this.jdbc.query(
                querySql,
                params,
                new BeanPropertyRowMapper<>(User_account.class));

        // Get total count for pagination
        String countSql = "SELECT COUNT(*) FROM user_accounts";
        int total = this.jdbc.queryForObject(countSql, new MapSqlParameterSource(), Integer.class);

        return new PageImpl<>(users, pg, total);
    }

    public Optional<User_account> find(long id) {

        String find_query = "SELECT * FROM user_accounts WHERE user_account_id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        try {
            User_account user = this.jdbc.queryForObject(
                    find_query,
                    params,
                    new BeanPropertyRowMapper<>(User_account.class));
            return Optional.of(user);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    public Optional<User_account> update(List<String> conditions, Map<String, String> value_for_update) {

        String update_query = "UPDATE user_accounts SET " + String.join(", ", conditions)
                + " WHERE user_account_id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource();

        value_for_update.forEach((key, value) -> {
            params.addValue(key, value);
        });

        int row_updated = this.jdbc.update(update_query, params);

        return row_updated != 0 ? find(Long.parseLong(value_for_update.get("id"))) : Optional.empty();

    }

    public Optional<User_account> findByUserName(String username) {
        String find_query = "Select * from user_accounts where username = :username";
        MapSqlParameterSource params = new MapSqlParameterSource("username", username);

        try {
            User_account user = this.jdbc.queryForObject(
                    find_query,
                    params,
                    new BeanPropertyRowMapper<>(User_account.class));
            return Optional.of(user);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    public Optional<User_account> findByUserEmail(String email) {
        String find_query = "Select * from user_accounts where email = :email";
        MapSqlParameterSource params = new MapSqlParameterSource("email", email);

        try {
            User_account user = this.jdbc.queryForObject(
                    find_query,
                    params,
                    new BeanPropertyRowMapper<>(User_account.class));
            return Optional.of(user);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    
}
