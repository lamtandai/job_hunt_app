package application.project.domain.Enumeration.ApplicationStatus;

public enum ApplicationStatus {
    APPLIED("applied"),
    REVIEWED("reviewed"),
    ACCEPTED("accepted"),
    REJECTED("rejected");

    private final String status;

    ApplicationStatus(String status) {
        this.status = "Your application has been " + status;
    }

    public String getStatus() {
        return status;
    }
}
