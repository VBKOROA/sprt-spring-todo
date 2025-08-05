package indiv.abko.todo.todo.application.port.in.command;

public record DeleteTodoCommand(
    Long id,
    String encodedPassword
) {
    
}
