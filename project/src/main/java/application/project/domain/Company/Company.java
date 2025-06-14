package application.project.domain.Company;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Company {

    private int company_id;
    private String company_name;
    private String description;
    private short industry_id;
    
    private int numberOfFollower;
    private int size;
    private String logo;
    private String location;
    private String website_url;
    private boolean verified;

    private Long created_by_user_id; 
    private Long updated_by_user_id; 

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss a", timezone = "GMT+7")
    private Instant createdAt;
    
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss a", timezone = "GMT+7")
    private Instant updatedAt;

}
