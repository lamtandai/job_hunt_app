package application.project.util.Mapper;

import application.project.domain.Application;
import application.project.domain.dto.response.ResultReturnedDTO;

public class ApplicationMapper {
    public static ResultReturnedDTO toApplicationResponse(Application application) {
        // Implement mapping logic as needed
        return new ResultReturnedDTO(null, application);
    }
}
