package application.project.domain.Application;

import java.time.LocalDate;

import application.project.domain.Enumeration.ApplicationStatus.ApplicationStatus;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Application {
    private long application_id;
    private long job_post_id;
    private ApplicationStatus application_status;
    private LocalDate apply_date;

}
