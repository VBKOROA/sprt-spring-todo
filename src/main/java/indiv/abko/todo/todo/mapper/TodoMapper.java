package indiv.abko.todo.todo.mapper;

import indiv.abko.todo.todo.comment.mapper.CommentMapper;
import indiv.abko.todo.todo.dto.TodoWithCommentsResp;
import indiv.abko.todo.todo.vo.Password;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import indiv.abko.todo.global.util.Encrypt;
import indiv.abko.todo.todo.dto.TodoCreateReq;
import indiv.abko.todo.todo.dto.TodoResp;
import indiv.abko.todo.todo.entity.Todo;

@Mapper(componentModel = "spring", uses = CommentMapper.class)
public interface TodoMapper {
    default Password toPassword(String rawPassword, Encrypt encrypt) {
        return new Password(rawPassword).encrypted(encrypt);
    }

    // qualifiedByName으로 "hashPassword"라는 이름의 메서드만 password 필드에 적용
    @Mapping(target = "password", expression = "java(toPassword(req.password(), encrypt))")
    Todo toTodo(TodoCreateReq req, Encrypt encrypts);

    TodoResp toTodoResp(Todo todo);

    @Mapping(target = "todo", source = "todo")
    @Mapping(target = "comments", source = "comments")
    TodoWithCommentsResp toTodoWithCommentResp(Todo todo);
}
