package indiv.abko.todo.todo.comment.dto;

import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.NotBlank;

public record CommentWriteReq(
    @Length(max=100)
    @NotBlank
    String content,

    @NotBlank
    String author,

    @NotBlank
    String password
) {
}
