package indiv.abko.todo.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public interface BusinessExceptionEnum {
    HttpStatus getStatus();
    String getMessage();
}
