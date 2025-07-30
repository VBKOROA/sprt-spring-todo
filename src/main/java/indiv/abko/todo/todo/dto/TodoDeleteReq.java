package indiv.abko.todo.todo.dto;

import jakarta.validation.constraints.NotBlank;

public record TodoDeleteReq(
    @NotBlank
    String password
) {

}
