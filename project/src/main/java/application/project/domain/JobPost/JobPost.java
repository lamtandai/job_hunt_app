package application.project.domain.JobPost;

import java.time.LocalDate;

import application.project.domain.Enumeration.JobStatus.JobStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data 
@AllArgsConstructor 
public class JobPost {
    private long job_post_id;
    private long recruiter_id;
    private String job_post_title;
    private String position;
    private String description;
    private String salary_range;
    private JobStatus job_status;
    private short number_of_recruitment; 
    
    private LocalDate posted_at;
    private LocalDate expired_at;

    private LocalDate created_at;
    private LocalDate updated_at;
    


   
}
