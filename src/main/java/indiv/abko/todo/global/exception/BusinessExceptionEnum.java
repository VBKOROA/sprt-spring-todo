package indiv.abko.todo.global.exception;

import org.springframework.http.HttpStatus;

public interface BusinessExceptionEnum {
    HttpStatus getStatus();
    String getMessage();
}
