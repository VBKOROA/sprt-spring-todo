package indiv.abko.todo.todo.domain.vo;

import indiv.abko.todo.global.exception.BusinessException;
import indiv.abko.todo.global.exception.BusinessExceptionEnum;
import indiv.abko.todo.todo.domain.exception.TodoExceptionEnum;
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
public class TodoTitle {
    private String title;

    public TodoTitle(final String title) {
        shouldValid(title);
        this.title = title;
    }

    private void shouldValid(final String title) {
        if(StringUtils.hasText(title) == false)  {
            throw new BusinessException(TodoExceptionEnum.TODO_TITLE_REQUIRED);
        }

        final boolean lengthNotValid = !(1 <= title.length() && title.length() <= 30);

        if (lengthNotValid) {
            throw new BusinessException(TodoExceptionEnum.TODO_TITLE_LENGTH_NOT_VALID);
        }
    }
}
