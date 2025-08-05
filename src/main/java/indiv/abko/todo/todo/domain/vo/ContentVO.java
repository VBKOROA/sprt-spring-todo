package indiv.abko.todo.todo.domain.vo;

import indiv.abko.todo.global.exception.BusinessException;
import indiv.abko.todo.todo.domain.exception.TodoExceptionEnum;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ContentVO {
    private String content;

    public ContentVO(final String content) {
        shouldValid(content);
        this.content = content;
    }

    private void shouldValid(final String content) {
        if(StringUtils.hasText(content) == false)  {
            throw new BusinessException(TodoExceptionEnum.CONTENT_REQUIRED);
        }

        final boolean notValid = !(1 <= content.length() && content.length() <= 200);

        if(notValid) {
            throw new BusinessException(TodoExceptionEnum.CONTENT_LENGTH_NOT_VALID);
        }
    }
}
