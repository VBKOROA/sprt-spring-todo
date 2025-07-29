package indiv.abko.todo.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ExceptionEnum {
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "원인 불명의 오류가 발생했습니다."),
    TODO_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 ID의 일정이 없습니다."),
    TODO_UPDATE_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "수정할 권한이 없습니다.");

    private final HttpStatus status;
    private final String message;

    ExceptionEnum(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
