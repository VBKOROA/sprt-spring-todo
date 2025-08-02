package indiv.abko.todo.todo.mapper;

import indiv.abko.todo.todo.comment.mapper.CommentMapper;
import indiv.abko.todo.todo.dto.TodoWithCommentsResp;
import indiv.abko.todo.todo.entity.vo.TodoPassword;
import indiv.abko.todo.todo.entity.vo.TodoTitle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import indiv.abko.todo.global.util.Encrypt;
import indiv.abko.todo.todo.dto.TodoCreateReq;
import indiv.abko.todo.todo.dto.TodoResp;
import indiv.abko.todo.todo.entity.Todo;

@Mapper(componentModel = "spring", uses = {CommentMapper.class})
public interface TodoMapper {
    default TodoPassword toTodoPassword(String rawPassword, Encrypt encrypt) {
        if (rawPassword == null) {return null;}
        return new TodoPassword(rawPassword).encrypted(encrypt);
    }

    default TodoTitle toTodoTitle(String title) {
        if (title == null) {return null;}
        return new TodoTitle(title);
    }

    default String toTitle(TodoTitle title) {
        return title.getTitle();
    }
}
