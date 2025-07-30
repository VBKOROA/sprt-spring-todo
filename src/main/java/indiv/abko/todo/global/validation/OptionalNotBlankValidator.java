package indiv.abko.todo.global.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OptionalNotBlankValidator implements ConstraintValidator<OptionalNotBlank, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value != null) {
            if(value.isBlank()) {
                return false;
            }
        }
        return true;
    }
}
