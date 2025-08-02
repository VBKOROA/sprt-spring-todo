package indiv.abko.todo.todo.repository;

import indiv.abko.todo.todo.dto.TodoSearchCondition;
import indiv.abko.todo.todo.domain.Todo;
import java.util.List;

public interface TodoRepositoryCustom {
    List<Todo> search(TodoSearchCondition condition);
}