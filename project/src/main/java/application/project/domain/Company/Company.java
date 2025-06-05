package application.project.domain.Company;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data 
@AllArgsConstructor 
public class Company {
    private int company_id;
    private String company_name;
    private int numberOfFollower;
    private int size;
    private String logo;
    private String description;
    private String location;
    private short industry_id;
    private String website_url;
    private Boolean verified;

    private LocalDate createdAt;
    private LocalDate updatedAt;
    

}
