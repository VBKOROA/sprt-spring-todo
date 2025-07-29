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
        HttpStatus status = e.getExceptionEnum().getStatus();
        String message = e.getExceptionEnum().getMessage();

        ApiResponse<?> response = new ApiResponse<>(
            status,
            message,
            e.getData()
        );

        return new ResponseEntity<>(response, status);
    }
}
