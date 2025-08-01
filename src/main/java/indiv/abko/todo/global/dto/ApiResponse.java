package indiv.abko.todo.global.dto;

import org.springframework.http.HttpStatus;

import indiv.abko.todo.global.exception.ExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "API 응답 DTO")
public record ApiResponse<T> (
    @Schema(description = "HTTP 상태 코드", example = "OK")
    HttpStatus status, // OK, CREATED 등의 문자열로 직렬화됨
    @Schema(description = "메시지", example = "요청이 잘못되었습니다.")
    String message,
    @Schema(description = "응답 데이터")
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
