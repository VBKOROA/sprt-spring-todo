package indiv.abko.todo.todo.domain.port.out;

import indiv.abko.todo.todo.domain.SearchTodosCriteria;
import indiv.abko.todo.todo.domain.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {
    Optional<Todo> findAggregate(Long id);
    List<Todo> searchSummaries(SearchTodosCriteria searchCriteria);

    Todo save(Todo todo);
    Todo saveComment(Todo todo);

    Optional<Todo> findSummary(Long id);

    void delete(Todo todo);
}
