package indiv.abko.todo.todo.domain.exception;

import indiv.abko.todo.global.exception.BusinessExceptionEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum TodoExceptionEnum implements BusinessExceptionEnum {
    TODO_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 ID의 일정이 없습니다."),
    TODO_PERMISSION_DENIED(HttpStatus.UNAUTHORIZED, "해당 일정에 대한 권한이 없습니다."),
    COMMENT_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "댓글은 10개를 초과할 수 없습니다."),
    TODO_TITLE_LENGTH_NOT_VALID(HttpStatus.BAD_REQUEST, "제목의 길이는 1 ~ 30자 이내여야 합니다."),
    TODO_TITLE_REQUIRED(HttpStatus.BAD_REQUEST, "제목은 필수 입력 값입니다."),
    TODO_PASSWORD_REQUIRED(HttpStatus.BAD_REQUEST, "비밀번호는 필수 입력 값입니다."),
    CONTENT_REQUIRED(HttpStatus.BAD_REQUEST, "내용은 필수 입력값입니다."),
    CONTENT_LENGTH_NOT_VALID(HttpStatus.BAD_REQUEST, "내용의 길이는 1 ~ 200자 이내여야 합니다.");

    private final HttpStatus status;
    private final String message;

    TodoExceptionEnum(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }
}
