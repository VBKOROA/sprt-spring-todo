package indiv.abko.todo.todo.adapter.in.rest.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OptionalNotBlankValidator implements ConstraintValidator<OptionalNotBlank, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.isBlank() == false;
    }
}
