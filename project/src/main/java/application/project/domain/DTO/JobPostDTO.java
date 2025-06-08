package application.project.domain.DTO;
import java.util.List;

import application.project.domain.Enumeration.JobStatus.JobStatus;
import application.project.domain.Skill.Skill;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class JobPostDTO {
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
