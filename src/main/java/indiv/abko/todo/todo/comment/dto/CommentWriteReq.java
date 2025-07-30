package indiv.abko.todo.todo.comment.dto;

public record CommentWriteReq(
    String content,
    String author,
    String password
) {
}
