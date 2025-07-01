package application.project.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReqIndustry {
    @NotBlank(message="This field cannot be blank!")
    private String idt_name;
    private boolean idt_deleted;

}
