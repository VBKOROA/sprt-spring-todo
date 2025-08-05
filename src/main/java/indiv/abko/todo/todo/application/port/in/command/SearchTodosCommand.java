package indiv.abko.todo.todo.application.port.in.command;

public record SearchTodosCommand(
    String orderBy,
    String title,
    String content,
    String author
) {
    
}
