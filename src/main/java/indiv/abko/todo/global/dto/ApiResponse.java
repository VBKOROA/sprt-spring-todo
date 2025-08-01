package indiv.abko.todo.global.dto;

import org.springframework.http.HttpStatus;

import indiv.abko.todo.global.exception.ExceptionEnum;

public record ApiResponse<T> (
    HttpStatus status, // OK, CREATED 등의 문자열로 직렬화됨
    String message,
    T data
) {
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(HttpStatus.OK, null, data);
    }

    public static <T> ApiResponse<T> error(ExceptionEnum e, T data) {
        return new ApiResponse<T>(e.getStatus(), e.getMessage(), data);
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<T>(HttpStatus.CREATED, null, data);
    }

    public static ApiResponse<Void> error(HttpStatus status, String message) {
        return new ApiResponse<Void>(status, message, null);
    }
}
