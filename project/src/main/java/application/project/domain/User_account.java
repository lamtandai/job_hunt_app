package application.project.domain;

import java.time.Instant;

import application.project.domain.Enumeration.UserGender;
import application.project.domain.Enumeration.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User_account {

    private long us_account_id;
    private String us_name;
    private String us_password;
    private String us_email;
    private String us_avatar;
    private String us_phone;
    private String us_location;
    private UserRole us_role;
    private UserGender us_gender;
    private String us_refresh_token;
    private Integer us_cpn_id;
    
    private boolean us_deleted;
    private Instant us_createdAt;
    private Instant us_updatedAt;


}
