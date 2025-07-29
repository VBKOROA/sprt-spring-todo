package indiv.abko.todo.common.dto;

import org.springframework.http.HttpStatus;

public record ApiResponse<T> (
    HttpStatus status,
    String message,
    T data
) {}
