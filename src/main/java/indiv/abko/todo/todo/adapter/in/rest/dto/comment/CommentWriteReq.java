package indiv.abko.todo.todo.adapter.in.rest.dto.comment;

import indiv.abko.todo.todo.adapter.in.rest.validation.ValidCommentContent;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "댓글 작성 DTO")
public record CommentWriteReq(
    @ValidCommentContent
    @NotBlank
    @Schema(description = "댓글 내용 / 1~200자 이내", example = "테스트 내용", requiredMode = Schema.RequiredMode.REQUIRED)
    String content,

    @NotBlank
    @Schema(description = "작성자", example = "테스트 작성자", requiredMode = Schema.RequiredMode.REQUIRED)
    String author,

    @NotBlank
    @Schema(description = "비밀번호", example = "테스트 비밀번호", requiredMode = Schema.RequiredMode.REQUIRED)
    String password
) {
}
