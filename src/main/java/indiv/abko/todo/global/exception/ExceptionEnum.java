package indiv.abko.todo.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ExceptionEnum {
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "원인 불명의 오류가 발생했습니다."),
    TODO_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 ID의 일정이 없습니다."),
    TODO_PERMISSION_DENIED(HttpStatus.UNAUTHORIZED, "해당 일정에 대한 권한이 없습니다."),
    COMMENT_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "댓글은 10개를 초과할 수 없습니다."),
    TODO_TITLE_LENGTH_NOT_VALID(HttpStatus.BAD_REQUEST, "제목의 길이는 1 ~ 30자 이내여야 합니다."),
    TODO_TITLE_REQUIRED(HttpStatus.BAD_REQUEST, "제목은 필수 입력 값입니다."),
    TODO_PASSWORD_REQUIRED(HttpStatus.BAD_REQUEST, "비밀번호는 필수 입력 값입니다."),
    TODO_CONTENT_REQUIRED(HttpStatus.BAD_REQUEST, "내용은 필수 입력값입니다."),
    TODO_CONTENT_LENGTH_NOT_VALID(HttpStatus.BAD_REQUEST, "내용의 길이는 1 ~ 200자 이내여야 합니다.");

    private final HttpStatus status;
    private final String message;

    ExceptionEnum(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
