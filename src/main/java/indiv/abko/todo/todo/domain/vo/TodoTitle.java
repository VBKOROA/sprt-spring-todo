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
public class TodoTitle {
    private String title;

    public TodoTitle(String title) {
        shouldValid(title);
        this.title = title;
    }

    private void shouldValid(String title) {
        if(StringUtils.hasText(title) == false)  {
            throw new BusinessException(ExceptionEnum.TODO_TITLE_REQUIRED);
        }

        final boolean lengthNotValid = !(1 <= title.length() && title.length() <= 30);

        if (lengthNotValid) {
            throw new BusinessException(ExceptionEnum.TODO_TITLE_LENGTH_NOT_VALID);
        }
    }
}
