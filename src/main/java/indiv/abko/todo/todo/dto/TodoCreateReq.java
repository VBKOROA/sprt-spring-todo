package indiv.abko.todo.todo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TodoCreateReq(
    @Size(min = 1, max = 30)
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
