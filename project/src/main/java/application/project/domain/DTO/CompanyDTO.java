package application.project.domain.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data 
@AllArgsConstructor 
public class CompanyDTO {

    private int size;
    private String name;
    private String field;
    private String description;
    private String location;
    
}
