package indiv.abko.todo.todo.domain;

import lombok.Builder;

@Builder
public record SearchTodosCriteria(
        String orderBy,
        String title,
        String content,
        String author
) {
}
