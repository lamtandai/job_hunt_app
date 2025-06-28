package application.project.domain.dto.response;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResJobPost {

    private long jp_id;
    private long jpRecruiterId;
    private long jpUpdatedBy;

    private byte jpNumberOfRecruitment ;
    private int jpCpnId;

    private String jpTitle;
    private String jpDescription;
    private String jpSalaryRange;
    private String jpStatus;

    private boolean jpDeleted;
    
    private Instant jpPostedAt;
    private Instant jpExpiredAt;
    private Instant jpCreatedAt;
    private Instant jpUpdatedAt;
    
}
