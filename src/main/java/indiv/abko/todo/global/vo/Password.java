package indiv.abko.todo.global.vo;

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
public class Password {
    private String password;

    public Password(String password) {
        shouldValid(password);
        this.password = password;
    }

    public Password encrypted(final Encrypt encrypt) {
        return new Password(encrypt.hash(this.password));
    }

    public void verify(final String password, final Encrypt encrypt) {
        if(encrypt.isHashEqual(password, this.password) == false) {
            throw new BusinessException(ExceptionEnum.TODO_PERMISSION_DENIED);
        }
    }

    private void shouldValid(String password) {
        if(StringUtils.hasText(password) == false)  {
            throw new BusinessException(ExceptionEnum.TODO_PASSWORD_REQUIRED);
        }
    }
}
