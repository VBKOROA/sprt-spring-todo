package indiv.abko.todo.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Todo와 댓글 응답 DTO")
public record TodoWithCommentsResp(
    @Schema(description = "Todo 정보")
    TodoResp todo,
    @Schema(description = "댓글 목록")
    List<CommentResp> comments
) {
    
}
