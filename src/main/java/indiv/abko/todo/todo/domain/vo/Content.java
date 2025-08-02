package indiv.abko.todo.todo.domain.vo;

import indiv.abko.todo.todo.presentation.exception.BusinessException;
import indiv.abko.todo.todo.presentation.exception.ExceptionEnum;
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
public class Content {
    private String content;

    public Content(String content) {
        shouldValid(content);
        this.content = content;
    }

    private void shouldValid(String content) {
        if(StringUtils.hasText(content) == false)  {
            throw new BusinessException(ExceptionEnum.CONTENT_REQUIRED);
        }

        final boolean notValid = !(1 <= content.length() && content.length() <= 200);

        if(notValid) {
            throw new BusinessException(ExceptionEnum.CONTENT_LENGTH_NOT_VALID);
        }
    }
}
