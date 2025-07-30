package indiv.abko.todo.todo.dto;

import indiv.abko.todo.todo.validation.ValidTodoTitle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TodoCreateReq(
    @ValidTodoTitle
    @NotBlank
    String title,

    @Size(min = 1, max = 200)
    @NotBlank
    String content,

    @NotBlank
    String author,

    @NotBlank
    String password
) {

}
