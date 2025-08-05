package indiv.abko.todo.todo.adapter.out.persistence.mapper;

import indiv.abko.todo.todo.adapter.out.persistence.entity.CommentJpaEntity;
import indiv.abko.todo.todo.domain.Comment;
import indiv.abko.todo.todo.domain.Todo;
import indiv.abko.todo.todo.domain.vo.ContentVO;
import indiv.abko.todo.todo.domain.vo.PasswordVO;
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
                .content(new ContentVO(commentJpaEntity.getContent()))
                .password(new PasswordVO(commentJpaEntity.getPassword()))
                .build();
    }

    public CommentJpaEntity toEntity(final Comment comment) {
        return CommentJpaEntity.builder()
                .id(comment.getId())
                .author(comment.getAuthor())
                .content(comment.getContent().getContent())
                .password(comment.getPassword().getPassword())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }
}
