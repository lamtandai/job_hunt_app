package application.project.util.Mapper;

import application.project.domain.Industry;
import application.project.domain.dto.response.ResultReturnedDTO;

public class IndustryMapper {
    public static ResultReturnedDTO toIndustryResponse(Industry industry) {
        // Implement mapping logic as needed
        return new ResultReturnedDTO(null, industry);
    }
}
