package indiv.abko.todo.todo.entity.vo;

import indiv.abko.todo.global.exception.BusinessException;
import indiv.abko.todo.global.exception.ExceptionEnum;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TodoContent {
    private String content;

    public TodoContent(String content) {
        shouldValid(content);
        this.content = content;
    }

    private void shouldValid(String content) {
        if(StringUtils.hasText(content) == false)  {
            throw new BusinessException(ExceptionEnum.TODO_CONTENT_REQUIRED);
        }

        final boolean notValid = !(1 <= content.length() && content.length() <= 200);

        if(notValid) {
            throw new BusinessException(ExceptionEnum.TODO_CONTENT_LENGTH_NOT_VALID);
        }
    }
}
