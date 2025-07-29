package indiv.abko.todo.todo.dto;

import java.time.LocalDateTime;

public record GetTodoResp(
    Long id,
    String title,
    String content,
    String author,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt
) {

}
