package indiv.abko.todo.todo.entity.vo;

import indiv.abko.todo.global.exception.BusinessException;
import indiv.abko.todo.global.exception.ExceptionEnum;
import indiv.abko.todo.global.util.Encrypt;
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
public class TodoPassword {
    private String password;

    public TodoPassword(String value) {
        shouldValid(value);
        this.password = value;
    }

    public TodoPassword encrypted(final Encrypt encrypt) {
        return new TodoPassword(encrypt.hash(this.password));
    }

    public void verify(final String password, final Encrypt encrypt) {
        if(encrypt.isHashEqual(password, this.password) == false) {
            throw new BusinessException(ExceptionEnum.TODO_PERMISSION_DENIED);
        }
    }

    private void shouldValid(final String passwordString) {
        if(StringUtils.hasText(passwordString) == false) {
            throw new BusinessException(ExceptionEnum.TODO_PASSWORD_NOT_VALID);
        }
    }
}
