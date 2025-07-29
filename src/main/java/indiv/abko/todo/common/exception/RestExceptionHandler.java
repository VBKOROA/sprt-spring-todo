package indiv.abko.todo.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import indiv.abko.todo.common.dto.ApiResponse;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessExcpetion(BusinessException e) {
        ExceptionEnum exceptionEnum = e.getExceptionEnum();
        HttpStatus status = exceptionEnum.getStatus();

        ApiResponse<?> response = ApiResponse.error(exceptionEnum, e.getData());

        return new ResponseEntity<>(response, status);
    }
}
