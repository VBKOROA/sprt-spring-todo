package indiv.abko.todo.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import indiv.abko.todo.global.dto.ApiResp;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResp<?>> handleBusinessExcpetion(BusinessException e) {
        BusinessExceptionEnum businessExceptionEnum = e.getBusinessExceptionEnum();
        HttpStatus status = businessExceptionEnum.getStatus();

        ApiResp<?> response = ApiResp.error(businessExceptionEnum);

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResp<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult errorResult = e.getBindingResult();
        var singleError = errorResult.getFieldError();

        // singleError가 null인 경우는 없지만
        // 만약 없다면 알 수 없는 오류 처리
        if(singleError == null) {
            var apiResponse = ApiResp.error(GlobalExceptionEnum.UNKNOWN_ERROR);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String field = singleError.getField();
        String message = singleError.getDefaultMessage();

        var apiResponse = ApiResp.error(HttpStatus.BAD_REQUEST, String.format("%s: %s", field, message));
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResp<?>> handleConstraintViolationException(ConstraintViolationException e) {
        var constraints = e.getConstraintViolations();

        if(constraints.isEmpty()) {
            var apiResponse = ApiResp.error(GlobalExceptionEnum.UNKNOWN_ERROR);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        var firstConstraint = constraints.iterator().next();

        String field = firstConstraint.getPropertyPath().toString();
        String message = firstConstraint.getMessage();

        var apiResponse = ApiResp.error(HttpStatus.BAD_REQUEST, String.format("%s: %s", field, message));
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
