package application.project.domain.dto.response;

import java.time.Instant;

import application.project.domain.Enumeration.UserRole;
import application.project.domain.User_account;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResUserDTO {
    private long us_account_id;
    private String us_name;
    private String us_email;
    private String us_avatar;
    private String us_phone;
    private UserRole us_role;
    private Instant us_createdAt;
    private Instant us_updatedAt;
    private Integer us_cpn_id;

    public ResUserDTO(User_account user) {
        this.us_account_id = user.getUs_account_id();
        this.us_name = user.getUs_name();
        this.us_email = user.getUs_email();
        this.us_phone = user.getUs_phone();
        this.us_role = user.getUs_role();
        this.us_avatar = user.getUs_avatar();
        this.us_createdAt = user.getUs_createdAt();
        this.us_updatedAt = user.getUs_updatedAt();
        this.us_cpn_id = user.getUs_cpn_id();
    }
}
