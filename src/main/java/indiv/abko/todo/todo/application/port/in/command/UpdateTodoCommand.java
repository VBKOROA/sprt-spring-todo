package indiv.abko.todo.todo.application.port.in.command;

import lombok.Builder;

@Builder
public record UpdateTodoCommand(
    Long id,
    String title,
    String author,
    String password
) {
} 
