package application.project.domain.dto.request;

import application.project.domain.Enumeration.UserGender;
import application.project.domain.Enumeration.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReqUserUpdateDTO {

    private String password;
    private String email;
    private String avatar;
    private String phone;
    private String location;
    private UserRole user_role;
    private UserGender user_gender;
    private int cpn_id;

}
