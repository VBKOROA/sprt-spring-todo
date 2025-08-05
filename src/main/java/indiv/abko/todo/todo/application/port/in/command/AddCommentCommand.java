package indiv.abko.todo.todo.application.port.in.command;

import lombok.Builder;

@Builder
public record AddCommentCommand(
    Long todoId,
    String content,
    String author,
    String password
) {
}