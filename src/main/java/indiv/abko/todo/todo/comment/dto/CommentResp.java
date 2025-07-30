package indiv.abko.todo.todo.comment.dto;

import java.time.LocalDateTime;

public record CommentResp(
    Long id,
    String content,
    String author,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt
) {
}