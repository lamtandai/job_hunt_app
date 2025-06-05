package application.project.domain.DTO;

import java.time.LocalDate;
import java.util.List;

import application.project.domain.Enumeration.JobStatus.JobStatus;
import application.project.domain.Skill.Skill;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data 
@AllArgsConstructor 
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
