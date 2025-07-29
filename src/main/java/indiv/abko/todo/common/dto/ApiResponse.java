package indiv.abko.todo.common.dto;

import org.springframework.http.HttpStatus;

public record ApiResponse<T> (
    HttpStatus status, // OK, CREATED 등의 문자열로 직렬화됨
    String message,
    T data
) {
    public static <T> ApiResponse<T> Ok(T data) {
        return new ApiResponse<>(HttpStatus.OK, "", data);
    }
}
