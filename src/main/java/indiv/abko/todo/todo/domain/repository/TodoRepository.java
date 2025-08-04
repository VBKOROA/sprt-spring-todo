package indiv.abko.todo.todo.domain.repository;

import indiv.abko.todo.todo.presentation.rest.dto.todo.TodoSearchCondition;
import indiv.abko.todo.todo.domain.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {
    Optional<Todo> findAggregate(Long id);
    List<Todo> searchSummaries(final TodoSearchCondition condition);

    Todo save(Todo todo);

    Optional<Todo> findSummary(Long id);

    void delete(Todo todo);
}
