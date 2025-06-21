package application.project.domain.dto.request;
import java.util.List;

import application.project.domain.Enumeration.JobStatus.JobStatus;
import application.project.domain.Skill.Skill;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ReqJobPostDTO {
    private String title;
    private String jobPosition;
    private List <Skill> skills;
    private String description;
    private String location;
    private String salaryRange;
    private String avatar;
    private JobStatus jobStatus;
    private int numberOfRecruitment; 
    
    
    
}
