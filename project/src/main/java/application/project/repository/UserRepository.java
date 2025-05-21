package application.project.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import application.project.domain.User;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTempalte;

    public UserRepository(JdbcTemplate jdbcTempalte) {
        this.jdbcTempalte = jdbcTempalte;
    }

    public Optional<User> createUser(User newUser){
        String insert_query = "INSERT INTO users (username, password, email, avatar, status) values(?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTempalte.update(connection->{
                    PreparedStatement ps = connection.prepareStatement(insert_query, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, newUser.getUsername());
                    ps.setString(2, newUser.getPassword());
                    ps.setString(3, newUser.getEmail());
                    ps.setString(4, newUser.getAvatar());
                    ps.setString(5, newUser.getStatus());
        return ps;
        },keyHolder);
        Number key = keyHolder.getKey();
        if (key != null) {
        Long newId = key.longValue();
        User fullNewTodo = find("id", String.valueOf(newId)).get().get(0);
        return fullNewTodo;
    }  return Optional.empty();
    }
    public Optional<User>find
}


