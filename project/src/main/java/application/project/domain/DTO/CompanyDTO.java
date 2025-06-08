package application.project.domain.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CompanyDTO {
    @NotBlank(message = "This field cannot be blank!")
    private String company_name;
    @NotBlank(message = "This field cannot be blank!")
    private String description;
    @NotNull(message = "This field cannot be blank!")
    private Short industry_id;
    
    private long created_by_user_id;
    private long updated_by_user_id;

    private int numberOfFollower;
    private int size;
    private String logo;
    private String location;
    private String website_url;
    private boolean verified;

}
