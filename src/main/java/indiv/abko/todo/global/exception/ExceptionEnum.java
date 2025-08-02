package indiv.abko.todo.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ExceptionEnum {
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "원인 불명의 오류가 발생했습니다."),
    TODO_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 ID의 일정이 없습니다."),
    TODO_PERMISSION_DENIED(HttpStatus.UNAUTHORIZED, "해당 일정에 대한 권한이 없습니다."),
    COMMENT_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "댓글은 10개를 초과할 수 없습니다."),
    TODO_PASSWORD_NOT_VALID(HttpStatus.BAD_REQUEST, "비밀번호가 유효하지 않습니다.");

    private final HttpStatus status;
    private final String message;

    ExceptionEnum(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
