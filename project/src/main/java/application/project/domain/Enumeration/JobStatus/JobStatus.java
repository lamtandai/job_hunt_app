package application.project.domain.Enumeration.JobStatus;

public enum JobStatus {
    AVAILABLE ("available"),
    UNAVAILABLE ("unavailable");

    private final String jobStatus;
    JobStatus(String status){
        this.jobStatus = "This job is " + status;
    }

    public String getStatus(){
        return this.jobStatus;
    }

}
