package indiv.abko.todo.global.dto;

import org.springframework.http.HttpStatus;

import indiv.abko.todo.global.exception.ExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "API 응답 DTO")
public record ApiResp<T> (
    @Schema(description = "HTTP 상태 코드", example = "OK")
    HttpStatus status, // OK, CREATED 등의 문자열로 직렬화됨
    @Schema(description = "메시지", example = "테스트 메시지")
    String message,
    @Schema(description = "응답 데이터")
    T data
) {
    public static <T> ApiResp<T> ok(T data) {
        return new ApiResp<>(HttpStatus.OK, null, data);
    }

    public static <T> ApiResp<T> error(ExceptionEnum e, T data) {
        return new ApiResp<T>(e.getStatus(), e.getMessage(), data);
    }

    public static <T> ApiResp<T> created(T data) {
        return new ApiResp<T>(HttpStatus.CREATED, null, data);
    }

    public static ApiResp<Void> error(HttpStatus status, String message) {
        return new ApiResp<Void>(status, message, null);
    }
}
