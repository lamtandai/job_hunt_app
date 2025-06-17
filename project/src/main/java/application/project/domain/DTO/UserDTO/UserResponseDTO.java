package application.project.domain.DTO.UserDTO;

import application.project.domain.Enumeration.UserRole.UserRole;
import application.project.domain.User.User_account;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private long user_account_id;
    private String username;
    private String email;
    private String avatar;
    private String phone;
    private UserRole user_role;

    public UserResponseDTO (User_account user){
        this.user_account_id = user.getUser_account_id();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.user_role = user.getUser_role();
        this.avatar = user.getAvatar();
    }
}
