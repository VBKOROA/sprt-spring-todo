package indiv.abko.todo.todo.application.port.in.command;

public record AddCommentCommand(
    Long todoId,
    String content,
    String author,
    String password
) {
}