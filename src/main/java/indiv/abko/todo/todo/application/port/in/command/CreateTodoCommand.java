package indiv.abko.todo.todo.application.port.in.command;

import lombok.Builder;

@Builder
public record CreateTodoCommand(
    String title,
    String content,
    String author,
    String password
) {
}