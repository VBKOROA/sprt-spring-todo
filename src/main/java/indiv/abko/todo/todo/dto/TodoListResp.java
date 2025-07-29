package indiv.abko.todo.todo.dto;

import java.time.LocalDateTime;
import java.util.List;

public record TodoListResp(
    List<TodoDto> todos
) {
    public record TodoDto(
        Long id,
        String title,
        String content,
        String author,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
    ) {}
}
