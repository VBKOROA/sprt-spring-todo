package indiv.abko.todo.todo.dto;

import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.NotBlank;

public record TodoUpdateReq(
    @Length(max = 30)
    @NotBlank
    String title,

    @NotBlank
    String author,

    @NotBlank
    String password
) {

}
