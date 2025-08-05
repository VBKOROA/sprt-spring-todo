package indiv.abko.todo.todo.application.port.in.command;

import indiv.abko.todo.todo.domain.SearchTodosCriteria;
import lombok.Builder;

@Builder
public record SearchTodosCommand(
    String orderBy,
    String title,
    String content,
    String author
) {
    public SearchTodosCriteria toCriteria() {
        return SearchTodosCriteria.builder()
                .orderBy(orderBy)
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
