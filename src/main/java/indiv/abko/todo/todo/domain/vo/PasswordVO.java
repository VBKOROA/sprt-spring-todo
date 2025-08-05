package indiv.abko.todo.todo.domain.vo;

import indiv.abko.todo.global.exception.BusinessException;
import indiv.abko.todo.todo.domain.port.out.PasswordEncoder;
import indiv.abko.todo.todo.domain.exception.TodoExceptionEnum;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PasswordVO {
    private String password;

    public PasswordVO(final String password) {
        shouldValid(password);
        this.password = password;
    }

    private void shouldValid(final String password) {
        if(StringUtils.hasText(password) == false)  {
            throw new BusinessException(TodoExceptionEnum.TODO_PASSWORD_REQUIRED);
        }
    }

    public boolean matches(final String otherRawPassword, final PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(otherRawPassword, this);
    }
}
