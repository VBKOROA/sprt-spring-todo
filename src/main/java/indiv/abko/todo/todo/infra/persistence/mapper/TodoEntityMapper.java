package indiv.abko.todo.todo.infra.persistence.mapper;

import indiv.abko.todo.todo.domain.Todo;
import indiv.abko.todo.todo.domain.vo.Content;
import indiv.abko.todo.todo.domain.vo.Password;
import indiv.abko.todo.todo.domain.vo.TodoTitle;
import indiv.abko.todo.todo.infra.persistence.entity.TodoJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TodoEntityMapper {
    private final CommentEntityMapper commentEntityMapper;

    public Todo toSummary(final TodoJpaEntity todoJpaEntity) {
        return Todo.builder()
                .id(todoJpaEntity.getId())
                .title(new TodoTitle(todoJpaEntity.getTitle()))
                .content(new Content(todoJpaEntity.getContent()))
                .author(todoJpaEntity.getAuthor())
                .password(new Password(todoJpaEntity.getPassword()))
                .createdAt(todoJpaEntity.getCreatedAt())
                .modifiedAt(todoJpaEntity.getModifiedAt())
                .build();
    }

    public Todo toAggregate(final TodoJpaEntity todoJpaEntity) {
        final Todo todo = toSummary(todoJpaEntity);
        todoJpaEntity.getComments().forEach(entity ->
                todo.addComment(commentEntityMapper.toDomain(entity, todo))
        );
        return todo;
    }

    public TodoJpaEntity toEntity(final Todo todo) {
        return TodoJpaEntity.builder()
                .id(todo.getId())
                .title(todo.getTitle().getTitle())
                .comments(todo.getComments().stream().map(commentEntityMapper::toEntity).collect(Collectors.toList()))
                .author(todo.getAuthor())
                .password(todo.getPassword().getPassword())
                .content(todo.getContent().getContent())
                .build();
    }
}
