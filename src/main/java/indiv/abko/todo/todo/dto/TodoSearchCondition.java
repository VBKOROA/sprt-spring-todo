package indiv.abko.todo.todo.dto;

public record TodoSearchCondition(
    String orderBy,
    String author
) {
    public boolean isNull() {
        return orderBy == null && author == null;
    }
}
