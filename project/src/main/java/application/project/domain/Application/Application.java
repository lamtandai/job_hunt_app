package application.project.domain.Application;

import java.time.LocalDate;

import application.project.domain.Enumeration.ApplicationStatus.ApplicationStatus;

public class Application {
    private long application_id;
    private long job_post_id;
    private ApplicationStatus application_status;
    private LocalDate apply_date;

    public long getApplication_id() {
        return application_id;
    }
    public void setId(long application_id) {
        this.application_id = application_id;
    }
    public long getJob_post_id() {
        return job_post_id;
    }
    public void setJob_post_id(long jobPost_id) {
        this.job_post_id = jobPost_id;
    }
    public ApplicationStatus getApplication_status() {
        return application_status;
    }
    public void setApplication_status(ApplicationStatus application_status) {
        this.application_status = application_status;
    }
    public LocalDate getApply_date() {
        return apply_date;
    }
    public void setApply_date(LocalDate apply_date) {
        this.apply_date = apply_date;
    }
    
}
