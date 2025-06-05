package application.project.domain.Job;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data 
@AllArgsConstructor 
public class Job {
    private int job_id;
    private String job_name;
    private int industry_id;
    private LocalDate created_at;
    private LocalDate updated_at;
}
