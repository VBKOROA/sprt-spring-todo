package indiv.abko.todo.todo.dto;

import java.util.List;

public record TodoListResp(
    List<TodoResp> todos
) {
}
