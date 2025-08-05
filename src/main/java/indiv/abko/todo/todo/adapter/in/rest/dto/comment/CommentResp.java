package indiv.abko.todo.todo.adapter.in.rest.dto.comment;

import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "댓글 응답 DTO")
@Builder
public record CommentResp(
    @Schema(description = "댓글 ID", example = "1")
    Long id,
    @Schema(description = "댓글 내용", example = "테스트")
    String content,
    @Schema(description = "댓글 작성자", example = "테스트")
    String author,
    @Schema(description = "댓글 작성일", example = "2025-01-01T12:00:00")
    LocalDateTime createdAt,
    @Schema(description = "댓글 수정일", example = "2025-01-01T12:00:00")
    LocalDateTime modifiedAt
) {
}