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

import application.project.domain.User_account;
import application.project.domain.Enumeration.UserRole;
import application.project.domain.dto.request.ReqUserRegisterDTO;

@Repository
public class UserRepository {
    private final NamedParameterJdbcTemplate jdbc;
    private final short Page_num_minimum = 1;
    private final short Limit_minimum = 10;

    public UserRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;

    }

    public Optional<Long> createUser(ReqUserRegisterDTO newUser) {
        String insert_query = "INSERT INTO user_accounts (us_name, us_password, us_email, us_role) VALUES (:us_name, :us_password, :us_email, :us_role)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("us_name", newUser.getUsername())
                .addValue("us_password", newUser.getPassword())
                .addValue("us_email", newUser.getEmail())
                .addValue("us_role", UserRole.JOB_SEEKER.name());

        this.jdbc.update(insert_query, params, keyHolder, new String[] { "us_account_id" });

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

        String find_query = "SELECT * FROM user_accounts WHERE us_account_id = :id";
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

    public Optional<User_account> update(List<String> conditions, Map<String, Object> value_for_update) {

        String update_query = "UPDATE user_accounts SET " + String.join(", ", conditions)
                + " WHERE us_account_id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource();

        value_for_update.forEach((key, value) -> {
            params.addValue(key, value);
        });

        int row_updated = this.jdbc.update(update_query, params);

        return row_updated != 0 ? find(Long.parseLong(value_for_update.get("id").toString())) : Optional.empty();

    }

    public Optional<User_account> findByUserName(String username) {
        String find_query = "Select * from user_accounts where us_name = :us_name";
        MapSqlParameterSource params = new MapSqlParameterSource("us_name", username);

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

    public Optional<User_account> findByUserNameAndRefreshToken(String token, String username) {
        String find_query = "Select * from user_accounts where us_name = :us_name AND us_refresh_token = :us_refresh_token";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("us_name", username)
                .addValue("us_refresh_token", token);

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
        String find_query = "Select * from user_accounts where us_email = :us_email";
        MapSqlParameterSource params = new MapSqlParameterSource("us_email", email);

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

    public void updateUserRefreshToken(String Token, long id) {

        String update_query = "UPDATE user_accounts SET us_refresh_token = :us_refresh_token WHERE us_account_id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("us_refresh_token", Token);

        this.jdbc.update(update_query, params);

    }

    public void deleteUserRefreshToken(long id) {
        String query = "UPDATE user_accounts SET us_refresh_token = :us_refresh_token WHERE us_account_id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("us_refresh_token", "");

        this.jdbc.update(query, params);
    }

    public void deleteUserById(long id){
        String query = "UPDATE user_accounts SET us_deleted = :us_deleted WHERE us_account_id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("us_deleted", true);

        this.jdbc.update(query, params);
    }

    public void deleteUserByCompanyId(long id){
        String query = "UPDATE user_accounts SET us_deleted = :us_deleted WHERE us_cpn_id = :us_cpn_id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("us_cpn_id", id)
                .addValue("us_deleted", true);

        this.jdbc.update(query, params);
    }

}
