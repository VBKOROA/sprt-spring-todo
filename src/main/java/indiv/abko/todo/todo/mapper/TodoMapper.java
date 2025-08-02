package indiv.abko.todo.todo.mapper;

import indiv.abko.todo.todo.comment.mapper.CommentMapper;
import indiv.abko.todo.todo.dto.TodoWithCommentsResp;
import indiv.abko.todo.todo.entity.vo.TodoPassword;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import indiv.abko.todo.global.util.Encrypt;
import indiv.abko.todo.todo.dto.TodoCreateReq;
import indiv.abko.todo.todo.dto.TodoResp;
import indiv.abko.todo.todo.entity.Todo;

@Mapper(componentModel = "spring", uses = {CommentMapper.class})
public interface TodoMapper {
    default TodoPassword toPassword(String rawPassword, Encrypt encrypt) {
        return new TodoPassword(rawPassword).encrypted(encrypt);
    }

    @Mapping(target = "password", expression = "java(toPassword(req.password(), encrypt))")
    Todo toTodo(TodoCreateReq req, Encrypt encrypt);

    TodoResp toTodoResp(Todo todo);

    @Mapping(target = "todo", source = "todo")
    @Mapping(target = "comments", source = "comments")
    TodoWithCommentsResp toTodoWithCommentResp(Todo todo);
}
