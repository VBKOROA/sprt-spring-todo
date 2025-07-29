package indiv.abko.todo.todo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import indiv.abko.todo.common.util.Encrypt;
import indiv.abko.todo.todo.dto.TodoCreateReq;
import indiv.abko.todo.todo.dto.CreateTodoResp;
import indiv.abko.todo.todo.dto.GetTodoResp;
import indiv.abko.todo.todo.dto.GetTodosResp;

@Mapper(componentModel = "spring", uses = Encrypt.class)
public interface TodoMapper {
    // qualifiedByName으로 "hashPassword"라는 이름의 메서드만 password 필드에 적용
    @Mapping(target = "password", qualifiedByName = "hashPassword")
    Todo toTodo(TodoCreateReq req);

    GetTodosResp.TodoDto toTodoDto(Todo todo);

    CreateTodoResp toCreateTodoResp(Todo todo);

    GetTodoResp toGetTodoResp(Todo todo);
}
