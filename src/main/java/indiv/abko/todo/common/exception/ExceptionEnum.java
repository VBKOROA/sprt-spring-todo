package indiv.abko.todo.common.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionEnum {
    TODO_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 ID의 일정이 없습니다.");

    private final HttpStatus status;
    private final String message;

    ExceptionEnum(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
