package indiv.abko.todo.todo.dto;

import indiv.abko.todo.todo.validation.ValidTodoTitle;
import jakarta.validation.constraints.NotBlank;

public record TodoUpdateReq(
    @ValidTodoTitle
    String title,

    String author,

    @NotBlank
    String password
) {

}
