package indiv.abko.todo.todo.adapter.out.persistence.mapper;

import indiv.abko.todo.todo.adapter.out.persistence.entity.TodoJpaEntity;
import indiv.abko.todo.todo.domain.Todo;
import indiv.abko.todo.todo.domain.vo.ContentVO;
import indiv.abko.todo.todo.domain.vo.PasswordVO;
import indiv.abko.todo.todo.domain.vo.TodoTitleVO;
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
                .title(new TodoTitleVO(todoJpaEntity.getTitle()))
                .content(new ContentVO(todoJpaEntity.getContent()))
                .author(todoJpaEntity.getAuthor())
                .password(new PasswordVO(todoJpaEntity.getPassword()))
                .createdAt(todoJpaEntity.getCreatedAt())
                .modifiedAt(todoJpaEntity.getModifiedAt())
                .build();
    }

    public Todo toAggregate(final TodoJpaEntity todoJpaEntity) {
        final Todo todo = toSummary(todoJpaEntity);
        final var comments = todoJpaEntity.getComments().stream()
                .map(comment -> commentEntityMapper.toDomain(comment, todo)).toList();
        todo.initCommentsViaRepository(comments);
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
                .createdAt(todo.getCreatedAt())
                .modifiedAt(todo.getModifiedAt())
                .build();
    }
}
