package indiv.abko.todo.todo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TodoCreateReq(
    @Size(max = 30)
    @NotBlank
    String title,

    @Size(max = 200)
    @NotBlank
    String content,

    @NotBlank
    String author,

    @NotBlank
    String password
) {

}
