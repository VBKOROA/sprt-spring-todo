package indiv.abko.todo.todo.dto;

public record TodoCreateReq(
    String title,
    String content,
    String author,
    String password
) {

}
