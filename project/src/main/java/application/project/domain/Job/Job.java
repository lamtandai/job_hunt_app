package application.project.domain.Job;


import java.time.Instant;

import application.project.domain.Enumeration.JobStatus.JobStatus;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Job {
    private int job_id;
    private String job_name;
    private short industry_id;
    
    private Instant created_at;
    private Instant updated_at;
}
