package indiv.abko.todo.todo.domain.repository;

import indiv.abko.todo.todo.presentation.rest.dto.todo.TodoSearchCondition;
import indiv.abko.todo.todo.domain.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {
    Optional<Todo> findByIdWithComments(Long id);
    List<Todo> search(final TodoSearchCondition condition);
}
