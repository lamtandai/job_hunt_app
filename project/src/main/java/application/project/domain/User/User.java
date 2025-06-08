package application.project.domain.User;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

import application.project.domain.Enumeration.UserRole.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private long user_account_id;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private String phone;
    private UserRole user_role;
    
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss a", timezone = "GMT+7")
    private Instant createdAt;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss a", timezone = "GMT+7")
    private Instant updatedAt;

    
}
