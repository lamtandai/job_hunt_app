package application.project.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReqCompanyDTO {

    @NotBlank(message = "This field cannot be blank!")
    private String company_name;
    @NotBlank(message = "This field cannot be blank!")
    private String description;

    private short industry_id;

    private long created_by;
    private long updated_by;

    private int number_of_follower;
    private int size;
    private String logo;
    private String location;
    private String website_url;
    private boolean verified;

}
