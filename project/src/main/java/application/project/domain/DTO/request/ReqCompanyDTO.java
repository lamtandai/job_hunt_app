package application.project.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ReqCompanyDTO {
    
    @NotBlank(message = "This field cannot be blank!")
    private String company_name;
    @NotBlank(message = "This field cannot be blank!")
    private String description;

    private short industry_id;
    
    private long created_by_user_id;
    private long updated_by_user_id;

    private int number_of_follower;
    private int size;
    private String logo;
    private String location;
    private String website_url;
    private boolean verified;

}
