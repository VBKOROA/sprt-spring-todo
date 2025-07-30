package indiv.abko.todo.todo.dto;

import java.util.List;
import indiv.abko.todo.todo.comment.dto.CommentResp;

public record TodoWithCommentsResp(
    TodoResp todo,
    List<CommentResp> comments
) {
    
}
