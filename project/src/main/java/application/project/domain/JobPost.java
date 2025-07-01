package application.project.domain;
import java.time.Instant;
import java.util.List;

import application.project.domain.Enumeration.JobStatus.JobStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobPost {

    private long jpId;
    private long jpRecruiterId;
    private byte jpNumberOfRecruitment ;
    private int jpCpnId;

    private String jpTitle;
    private String jpDescription;
    private String jpSalaryRange;
    private String jp_location;
    private List<String> jpSkills;
    private JobStatus jpStatus;
    
    private long jpUpdatedBy;

    private boolean jpDeleted;
    private Instant jpPostedAt;
    private Instant jpExpiredAt;
    private Instant jpCreatedAt;
    private Instant jpUpdatedAt;
    
}
