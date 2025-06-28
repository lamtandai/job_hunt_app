package application.project.util.Mapper;

import application.project.domain.Company;
import application.project.domain.dto.response.ResultReturnedDTO;

public class CompanyMapper {
    public static ResultReturnedDTO toCompanyResponse(Company company) {
        // Implement mapping logic as needed
        return new ResultReturnedDTO(null, company);
    }
}
