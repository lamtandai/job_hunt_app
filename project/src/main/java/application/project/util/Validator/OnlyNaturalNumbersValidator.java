package application.project.util.Validator;

import java.util.List;

import application.project.util.CustomAnnotation.OnlyNaturalNumbers;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class OnlyNaturalNumbersValidator implements ConstraintValidator<OnlyNaturalNumbers, List<String>> {
    private static final Pattern pattern = Pattern.compile("^[1-9]\\d*$");

    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        if (values == null) return true;
        return values.stream().allMatch(val -> pattern.matcher(val).matches());
    }
}