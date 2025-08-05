package indiv.abko.todo.todo.application.port.in.command;

import lombok.Builder;

@Builder
public record SearchTodosCommand(
    String orderBy,
    String title,
    String content,
    String author
) {
    
}
