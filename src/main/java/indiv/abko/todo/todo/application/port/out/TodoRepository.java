package indiv.abko.todo.todo.application.port.out;

import indiv.abko.todo.todo.application.port.in.command.SearchTodosCommand;
import indiv.abko.todo.todo.domain.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {
    Optional<Todo> findAggregate(Long id);
    List<Todo> searchSummaries(final SearchTodosCommand searchCommand);

    Todo save(Todo todo);

    Optional<Todo> findSummary(Long id);

    void delete(Todo todo);
}
