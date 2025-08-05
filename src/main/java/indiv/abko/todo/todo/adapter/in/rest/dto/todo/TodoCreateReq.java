package indiv.abko.todo.todo.adapter.in.rest.dto.todo;

import indiv.abko.todo.todo.adapter.in.rest.validation.ValidTodoContent;
import indiv.abko.todo.todo.adapter.in.rest.validation.ValidTodoTitle;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Schema(description = "새 Todo 생성 요청")
@Builder
public record TodoCreateReq(
    @ValidTodoTitle
    @NotBlank
    @Schema(description = "Todo 제목 / 1~30자 이내", example = "테스트", requiredMode = Schema.RequiredMode.REQUIRED)
    String title,

    @ValidTodoContent
    @NotBlank
    @Schema(description = "Todo 내용 / 1~200자 이내", example = "테스트", requiredMode = Schema.RequiredMode.REQUIRED)
    String content,

    @NotBlank
    @Schema(description = "Todo 작성자", example = "테스트", requiredMode = Schema.RequiredMode.REQUIRED)
    String author,

    @NotBlank
    @Schema(description = "Todo 비밀번호", example = "테스트", requiredMode = Schema.RequiredMode.REQUIRED)
    String password
) {

}
