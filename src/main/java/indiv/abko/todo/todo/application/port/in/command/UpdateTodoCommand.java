package indiv.abko.todo.todo.application.port.in.command;

public record UpdateTodoCommand(
    Long id,
    String title,
    String author,
    String password
) {
} 
