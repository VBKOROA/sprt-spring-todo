package indiv.abko.todo.todo.application.port.in.command;

import lombok.Builder;

@Builder
public record DeleteTodoCommand(
    Long id,
    String encodedPassword
) {
    
}
