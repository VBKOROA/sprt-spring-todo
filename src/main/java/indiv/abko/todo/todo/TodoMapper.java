package indiv.abko.todo.todo;

import org.mapstruct.Mapper;

import indiv.abko.todo.todo.dto.CreateTodoReq;
import indiv.abko.todo.todo.dto.CreateTodoResp;
import indiv.abko.todo.todo.dto.GetTodosResp;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    Todo toTodo(CreateTodoReq req);

    GetTodosResp.TodoDto toTodoDto(Todo todo);

    CreateTodoResp toCreateTodoResp(Todo todo);
}
