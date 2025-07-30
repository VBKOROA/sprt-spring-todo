package indiv.abko.todo.todo.dto;

public record TodoSearchCondition(
    String orderBy,
    String title,
    String content,
    String author
) {
}
