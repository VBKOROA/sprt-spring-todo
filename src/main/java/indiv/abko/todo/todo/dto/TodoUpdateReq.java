package indiv.abko.todo.todo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TodoUpdateReq(
    @Size(min = 1, max = 30)
    @NotBlank
    String title,

    @NotBlank
    String author,

    @NotBlank
    String password
) {

}
