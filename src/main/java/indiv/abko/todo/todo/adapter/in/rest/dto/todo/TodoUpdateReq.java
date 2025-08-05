package indiv.abko.todo.todo.adapter.in.rest.dto.todo;

import indiv.abko.todo.todo.adapter.in.rest.validation.OptionalNotBlank;
import indiv.abko.todo.todo.adapter.in.rest.validation.ValidTodoTitle;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Todo 업데이트 요청 DTO")
public record TodoUpdateReq(
    @ValidTodoTitle
    @OptionalNotBlank
    @Schema(description = "Todo 제목 / 1~30자 이내", example = "수정할 제목", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    String title,

    @OptionalNotBlank
    @Schema(description = "Todo 작성자", example = "수정할 작성자", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    String author,

    @NotBlank
    @Schema(description = "Todo 비밀번호", example = "비밀번호", requiredMode = Schema.RequiredMode.REQUIRED)
    String password
) {

}
