package indiv.abko.todo.todo.mapper;

import indiv.abko.todo.todo.comment.mapper.CommentMapper;
import indiv.abko.todo.todo.dto.TodoWithCommentsResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import indiv.abko.todo.global.util.Encrypt;
import indiv.abko.todo.todo.dto.TodoCreateReq;
import indiv.abko.todo.todo.dto.TodoResp;
import indiv.abko.todo.todo.entity.Todo;

@Mapper(componentModel = "spring", uses = {Encrypt.class, CommentMapper.class})
public interface TodoMapper {
    // qualifiedByName으로 "hashPassword"라는 이름의 메서드만 password 필드에 적용
    @Mapping(target = "password", qualifiedByName = "hashPassword")
    Todo toTodo(TodoCreateReq req);

    TodoResp toTodoResp(Todo todo);

    @Mapping(target = "todo", source = "todo")
    @Mapping(target = "comments", source = "comments")
    TodoWithCommentsResp toTodoWithCommentResp(Todo todo);
}
