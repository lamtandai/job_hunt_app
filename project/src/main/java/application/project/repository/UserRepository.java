package application.project.repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import application.project.domain.DTO.UserRegisterDTO;
import application.project.domain.Enumeration.UserRole.UserRole;
import application.project.domain.User.User;

@Repository
public class UserRepository {
    private final NamedParameterJdbcTemplate jdbc;

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

    public Optional<List<User>> findAll() {
        String findAll_query = "SELECT * FROM user_accounts";
        return Optional.ofNullable(this.jdbc.query(
                findAll_query,
                new BeanPropertyRowMapper<>(User.class)));
    }

    public Optional<User> find(long id) {

        String find_query = "SELECT * FROM user_accounts WHERE user_account_id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        try {
            User user = this.jdbc.queryForObject(
                    find_query,
                    params,
                    new BeanPropertyRowMapper<>(User.class));
            return Optional.of(user);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    public Optional <User> update(User user){
        String update_query = """
        UPDATE user_accounts
           SET
             password = :password,
             email    = :email,
             avatar   = :avatar,
             phone    = :phone
         WHERE user_account_id = :id
    """;
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("id",         user.getUser_account_id())
            .addValue("password",   user.getPassword())
            .addValue("email",      user.getEmail())
            .addValue("avatar",     user.getAvatar())
            .addValue("phone",      user.getPhone());
            
           
        int row_updated = this.jdbc.update(update_query, params);
        
        return row_updated != 0 ? find(user.getUser_account_id()) : Optional.empty();
      
    }
    public Optional<User> findByUserName(String username){
        String find_query = "Select * from user_accounts where username = :username";
        MapSqlParameterSource params = new MapSqlParameterSource("username", username);

        try {
            User user = this.jdbc.queryForObject(
                    find_query,
                    params,
                    new BeanPropertyRowMapper<>(User.class));
            return Optional.of(user);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }
}
