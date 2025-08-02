package indiv.abko.todo.todo.vo;

import indiv.abko.todo.global.exception.BusinessException;
import indiv.abko.todo.global.exception.ExceptionEnum;
import indiv.abko.todo.global.util.Encrypt;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {
    private String value;

    public Password(String value) {
        shouldValid(value);
        this.value = value;
    }

    public Password encrypted(final Encrypt encrypt) {
        return new Password(encrypt.hash(this.value));
    }

    public void verify(final String password, final Encrypt encrypt) {
        if(encrypt.isHashEqual(password, value) == false) {
            throw new BusinessException(ExceptionEnum.TODO_PERMISSION_DENIED);
        }
    }

    private void shouldValid(final String passwordString) {
        if(StringUtils.hasText(passwordString) == false) {
            throw new BusinessException(ExceptionEnum.TODO_PASSWORD_NOT_VALID);
        }
    }
}
