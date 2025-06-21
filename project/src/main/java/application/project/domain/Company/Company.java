package application.project.domain.Company;

import java.time.Instant;



import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Company {

    private int company_id;
    private String company_name;
    private String description;

    private short industry_id;
    private int number_of_follower;
    private int size;
    
    private String logo;
    private String location;
    private String website_url;
    private boolean verified;

    private Long created_by_user_id; 
    private Long updated_by_user_id; 

    private Instant createdAt;
    private Instant updatedAt;

}
