package indiv.abko.todo.todo.adapter.in.rest.mapper;

import indiv.abko.todo.todo.adapter.in.rest.dto.todo.TodoResp;
import indiv.abko.todo.todo.application.port.out.PasswordEncoder;
import indiv.abko.todo.todo.domain.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TodoMapper {
    private final PasswordEncoder passwordEncoder;
    private final CommentMapper commentMapper;

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

    public List<TodoResp> toTodoResps(final List<Todo> todo) {
        return todo.stream().map(this::toTodoResp).toList();
    }
}
