package application.project.domain;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data 
@AllArgsConstructor 
@Builder
public class User {
    private final long id;
    private final String username;
    private String password;
    private String email;
    private String avatar;
    private String status;
    private String phone;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
