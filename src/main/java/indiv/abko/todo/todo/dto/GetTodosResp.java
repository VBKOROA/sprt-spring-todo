package indiv.abko.todo.todo.dto;

import java.time.LocalDateTime;
import java.util.List;

public record GetTodosResp(
    List<TodoDto> todos
) {
    record TodoDto(
        String title,
        String content,
        String author,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
    ) {}
}
