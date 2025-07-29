package indiv.abko.todo.todo.repository;

import java.util.List;

import indiv.abko.todo.todo.dto.TodoSearchCondition;
import indiv.abko.todo.todo.entity.Todo;

public interface TodoQRepository {
    List<Todo> fetchFilteredTodos(TodoSearchCondition condition);
}
