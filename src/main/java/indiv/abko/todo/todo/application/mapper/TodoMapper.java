package indiv.abko.todo.todo.application.mapper;

import indiv.abko.todo.todo.application.port.out.PasswordEncoder;
import indiv.abko.todo.todo.domain.Todo;
import indiv.abko.todo.todo.domain.vo.Content;
import indiv.abko.todo.todo.domain.vo.TodoTitle;
import indiv.abko.todo.todo.presentation.rest.dto.comment.CommentResp;
import indiv.abko.todo.todo.presentation.rest.dto.todo.TodoCreateReq;
import indiv.abko.todo.todo.presentation.rest.dto.todo.TodoResp;
import indiv.abko.todo.todo.presentation.rest.dto.todo.TodoWithCommentsResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TodoMapper {
    private final PasswordEncoder passwordEncoder;
    private final CommentMapper commentMapper;

    public Todo toTodo(final TodoCreateReq dto) {
        return Todo.builder()
                .title(new TodoTitle(dto.title()))
                .author(dto.author())
                .content(new Content(dto.content()))
                .password(passwordEncoder.encode(dto.password()))
                .build();
    }

    public TodoResp toTodoResp(final Todo todo) {
        return TodoResp.builder()
                .id(todo.getId())
                .title(todo.getTitle().getTitle())
                .content(todo.getContent().getContent())
                .author(todo.getAuthor())
                .createdAt(todo.getCreatedAt())
                .modifiedAt(todo.getModifiedAt())
                .build();
    }

    public TodoWithCommentsResp toTodoWithCommentsResp(final Todo todo) {
        final TodoResp todoResp = toTodoResp(todo);
        final List<CommentResp> commentResps = commentMapper.toCommentResps(todo.getComments());
        return new TodoWithCommentsResp(todoResp, commentResps);
    }
}
