package indiv.abko.todo.todo.infra.persistence.mapper;

import indiv.abko.todo.todo.domain.Comment;
import indiv.abko.todo.todo.domain.Todo;
import indiv.abko.todo.todo.domain.vo.Content;
import indiv.abko.todo.todo.domain.vo.Password;
import indiv.abko.todo.todo.infra.persistence.entity.CommentJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class CommentEntityMapper {
    // 읽기 및 수정
    public Comment toDomain(final CommentJpaEntity commentJpaEntity, Todo todo) {
        return Comment.builder()
                .id(commentJpaEntity.getId())
                .createdAt(commentJpaEntity.getCreatedAt())
                .modifiedAt(commentJpaEntity.getModifiedAt())
                .todo(todo)
                .author(commentJpaEntity.getAuthor())
                .content(new Content(commentJpaEntity.getContent()))
                .password(new Password(commentJpaEntity.getPassword()))
                .build();
    }

    public CommentJpaEntity toEntity(final Comment comment) {
        return CommentJpaEntity.builder()
                .id(comment.getId())
                .author(comment.getAuthor())
                .content(comment.getContent().getContent())
                .password(comment.getPassword().getPassword())
                .build();
    }
}
