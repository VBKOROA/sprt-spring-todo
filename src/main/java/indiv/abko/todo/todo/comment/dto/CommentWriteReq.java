package indiv.abko.todo.todo.comment.dto;

import indiv.abko.todo.todo.comment.validation.ValidCommentContent;
import jakarta.validation.constraints.NotBlank;

public record CommentWriteReq(
    @ValidCommentContent
    @NotBlank
    String content,

    @NotBlank
    String author,

    @NotBlank
    String password
) {
}
