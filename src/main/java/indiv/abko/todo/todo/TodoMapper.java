package indiv.abko.todo.todo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import indiv.abko.todo.common.util.Encrypt;
import indiv.abko.todo.todo.dto.CreateTodoReq;
import indiv.abko.todo.todo.dto.CreateTodoResp;
import indiv.abko.todo.todo.dto.GetTodosResp;

@Mapper(componentModel = "spring", uses = Encrypt.class)
public interface TodoMapper {
    @Mapping(target = "password", expression = "java(encrypt.hash(todoReq.password()))")
    Todo toTodo(CreateTodoReq req);

    GetTodosResp.TodoDto toTodoDto(Todo todo);

    CreateTodoResp toCreateTodoResp(Todo todo);
}
