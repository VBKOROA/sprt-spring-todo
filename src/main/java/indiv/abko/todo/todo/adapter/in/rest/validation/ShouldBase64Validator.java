package indiv.abko.todo.todo.adapter.in.rest.validation;

import java.util.Base64;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ShouldBase64Validator implements ConstraintValidator<ShouldBase64, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) {
            return true;
        }

        if(value.isBlank()) {
            return false;
        }

        try {
            Base64.getDecoder().decode(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
