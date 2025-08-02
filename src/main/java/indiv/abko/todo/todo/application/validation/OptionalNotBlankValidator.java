package indiv.abko.todo.todo.application.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OptionalNotBlankValidator implements ConstraintValidator<OptionalNotBlank, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (isExistButBlank(value)) {
            return false;
        }
        return true;
    }

    private boolean isExistButBlank(String value) {
        return value != null && value.isBlank();
    }
}
