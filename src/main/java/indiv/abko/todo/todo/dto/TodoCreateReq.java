package indiv.abko.todo.todo.dto;

import indiv.abko.todo.todo.validation.ValidTodoContent;
import indiv.abko.todo.todo.validation.ValidTodoTitle;
import jakarta.validation.constraints.NotBlank;

public record TodoCreateReq(
    @ValidTodoTitle
    @NotBlank
    String title,

    @ValidTodoContent
    @NotBlank
    String content,

    @NotBlank
    String author,

    @NotBlank
    String password
) {

}
