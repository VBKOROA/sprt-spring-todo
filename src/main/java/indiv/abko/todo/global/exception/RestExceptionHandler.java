package indiv.abko.todo.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import indiv.abko.todo.global.dto.ApiResponse;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessExcpetion(BusinessException e) {
        ExceptionEnum exceptionEnum = e.getExceptionEnum();
        HttpStatus status = exceptionEnum.getStatus();

        ApiResponse<?> response = ApiResponse.error(exceptionEnum, e.getData());

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult errorResult = e.getBindingResult();
        var singleError = errorResult.getFieldError();

        // singleError가 null인 경우는 없지만
        // 만약 없다면 알 수 없는 오류 처리
        if(singleError == null) {
            var apiResponse = ApiResponse.error(ExceptionEnum.UNKNOWN_ERROR, "알 수 없는 오류가 발생했습니다.");
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String field = singleError.getField();
        String message = singleError.getDefaultMessage();

        var apiResponse = ApiResponse.error(HttpStatus.BAD_REQUEST, String.format("%s: %s", field, message));
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
