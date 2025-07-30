package indiv.abko.todo.todo.dto;

import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.NotBlank;

public record TodoCreateReq(
    @Length(max = 30)
    @NotBlank
    String title,

    @Length(max = 200)
    @NotBlank
    String content,

    @NotBlank
    String author,

    @NotBlank
    String password
) {

}
