package indiv.abko.todo.todo.domain.vo;

import indiv.abko.todo.todo.presentation.exception.BusinessException;
import indiv.abko.todo.todo.presentation.exception.ExceptionEnum;
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

    public Password(final String password) {
        shouldValid(password);
        this.password = password;
    }

    private void shouldValid(final String password) {
        if(StringUtils.hasText(password) == false)  {
            throw new BusinessException(ExceptionEnum.TODO_PASSWORD_REQUIRED);
        }
    }
}
