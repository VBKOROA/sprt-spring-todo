package indiv.abko.todo.todo.dto;

public record CreateTodoReq(
    String title,
    String content,
    String author,
    String password
) {

}
