package indiv.abko.todo.todo.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentWriteReq(
    @Size(max=100)
    @NotBlank
    String content,

    @NotBlank
    String author,

    @NotBlank
    String password
) {
}
