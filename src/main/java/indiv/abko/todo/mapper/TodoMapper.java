package indiv.abko.todo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import indiv.abko.todo.global.util.Encrypt;
import indiv.abko.todo.dto.TodoCreateReq;
import indiv.abko.todo.dto.TodoResp;
import indiv.abko.todo.entity.Todo;

@Mapper(componentModel = "spring", uses = Encrypt.class)
public interface TodoMapper {
    // qualifiedByName으로 "hashPassword"라는 이름의 메서드만 password 필드에 적용
    @Mapping(target = "password", qualifiedByName = "hashPassword")
    Todo toTodo(TodoCreateReq req);

    TodoResp toTodoResp(Todo todo);
}
