package indiv.abko.todo.todo.repository;

import indiv.abko.todo.todo.dto.TodoListResp;
import indiv.abko.todo.todo.dto.TodoSearchCondition;

public interface TodoQRepository {
    TodoListResp fetchFilteredTodos(TodoSearchCondition condition);
}
