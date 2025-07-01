package application.project.util.CustomAnnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import application.project.util.Validator.OnlyNaturalNumbersValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = OnlyNaturalNumbersValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface OnlyNaturalNumbers {
    String message() default "This field contains only natural numbers";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}