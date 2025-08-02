package indiv.abko.todo.todo.domain.repository;

import indiv.abko.todo.todo.presentation.rest.dto.todo.TodoSearchCondition;
import indiv.abko.todo.todo.domain.Todo;
import java.util.List;

public interface TodoRepositoryCustom {
    List<Todo> search(final TodoSearchCondition condition);
}