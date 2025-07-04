package application.project.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqUserRegisterDTO {
    
    @NotBlank(message="This field cannot be blank!")
    private String username;

    @Pattern(regexp="^(?=.*?[0-9])(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[^0-9A-Za-z]).{8,}$",
             message="The password must contain at least one each of a number, uppercase letter, lowercase letter, and non-alphanumeric, and the length must be greater than 8")
    private String password;

    @NotBlank(message="This field cannot be blank!")
    @Pattern(regexp= "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
             message= "Invalid email, try again!")
    private String email;
   
}
