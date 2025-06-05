package application.project.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor 
public class UserRegisterDTO {
    
    private String username;
    private String password;
    private String email;
   
}
