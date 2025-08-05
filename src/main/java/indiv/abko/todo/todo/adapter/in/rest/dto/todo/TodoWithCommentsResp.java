package indiv.abko.todo.todo.adapter.in.rest.dto.todo;

import java.util.List;
import indiv.abko.todo.todo.adapter.in.rest.dto.comment.CommentResp;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Todo와 댓글 응답 DTO")
public record TodoWithCommentsResp(
    @Schema(description = "Todo 정보")
    TodoResp todo,
    @Schema(description = "댓글 목록")
    List<CommentResp> comments
) {
    
}
