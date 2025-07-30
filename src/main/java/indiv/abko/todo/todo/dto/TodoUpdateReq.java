package indiv.abko.todo.todo.dto;

import indiv.abko.todo.global.validation.OptionalNotBlank;
import indiv.abko.todo.todo.validation.ValidTodoTitle;
import jakarta.validation.constraints.NotBlank;

public record TodoUpdateReq(
    @ValidTodoTitle
    @OptionalNotBlank
    String title,

    @OptionalNotBlank
    String author,

    @NotBlank
    String password
) {

}
