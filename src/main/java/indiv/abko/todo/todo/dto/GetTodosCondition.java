package indiv.abko.todo.todo.dto;

public record GetTodosCondition(
    String orderBy,
    String author
) {
    public boolean isNull() {
        return orderBy == null && author == null;
    }
}
