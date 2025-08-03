package indiv.abko.todo.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum GlobalExceptionEnum implements BusinessExceptionEnum{
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "원인 불명의 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;

    GlobalExceptionEnum(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }
}
