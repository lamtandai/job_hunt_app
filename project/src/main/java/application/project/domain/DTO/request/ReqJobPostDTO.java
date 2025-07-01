package application.project.domain.dto.request;
import java.time.Instant;
import java.util.List;

import application.project.domain.Enumeration.JobStatus.JobStatus;
import application.project.util.CustomAnnotation.OnlyNaturalNumbers;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReqJobPostDTO {

    private byte jpNumberOfRecruitment ;

    @NotBlank(message="This field cannot be blank!")
    private String jpTitle;
    @NotBlank(message="This field cannot be blank!")
    private String jpDescription;
    @NotBlank(message="This field cannot be blank!")
    private String jpSalaryRange;
    @NotNull
    private JobStatus jpStatus;
    @NotBlank(message="This field cannot be blank!")
    private String location; 

    @OnlyNaturalNumbers
    private List<String> jpSkills;

    private boolean jpDeleted;

    private Instant jpPostedAt;
    private Instant jpExpiredAt;
   
    
}
