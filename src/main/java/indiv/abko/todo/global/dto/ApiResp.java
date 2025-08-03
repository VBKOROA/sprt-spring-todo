package indiv.abko.todo.global.dto;

import org.springframework.http.HttpStatus;

import indiv.abko.todo.global.exception.BusinessExceptionEnum;
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

    public static <T> ApiResp<T> error(BusinessExceptionEnum e) {
        return new ApiResp<>(e.getStatus(), e.getMessage(), null);
    }

    public static <T> ApiResp<T> created(T data) {
        return new ApiResp<>(HttpStatus.CREATED, null, data);
    }

    public static ApiResp<Void> error(HttpStatus status, String message) {
        return new ApiResp<>(status, message, null);
    }
}
