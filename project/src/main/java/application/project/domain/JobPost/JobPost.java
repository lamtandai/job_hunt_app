package application.project.domain.JobPost;

import java.time.Instant;
import application.project.domain.Enumeration.JobStatus.JobStatus;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class JobPost {
    private long job_post_id;
    private long job_id;
    private long recruiter_id;

    private short number_of_recruitment; 
    private String salary_range;
    private String description;
    
    private JobStatus job_status;
    
    private Instant posted_at;
    private Instant expired_at;

    private Instant created_at;
    private Instant updated_at;
    


   
}
