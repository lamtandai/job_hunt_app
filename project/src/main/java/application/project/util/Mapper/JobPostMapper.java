package application.project.util.Mapper;

import application.project.domain.JobPost;
import application.project.domain.dto.response.ResultReturnedDTO;

public class JobPostMapper {
    public static ResultReturnedDTO toJobPostResponse(JobPost jobPost) {
        // Implement mapping logic as needed
        return new ResultReturnedDTO(null, jobPost);
    }
}
