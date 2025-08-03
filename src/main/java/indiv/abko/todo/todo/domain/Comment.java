package indiv.abko.todo.todo.domain;

import java.time.LocalDateTime;

import indiv.abko.todo.todo.domain.common.BaseTimeEntity;
import indiv.abko.todo.todo.domain.vo.Content;
import indiv.abko.todo.todo.domain.vo.Password;
import indiv.abko.todo.todo.presentation.rest.dto.comment.CommentResp;
import indiv.abko.todo.todo.presentation.rest.dto.comment.CommentWriteReq;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 댓글 내용
    @Embedded
    private Content content;

    // 작성자명
    private String author;

    // 비밀번호
    @Embedded
    private Password password;

    // Todo 엔티티와의 연관관계
    @ManyToOne
    private Todo todo;

    @Builder
    public Comment(final Content content, final String author, final Password password) {
        this.content = content;
        this.author = author;
        this.password = password;
    }

    public void atTodo(final Todo todo) {
        this.todo = todo;
    }

    public CommentResp toCommentResp() {
        return CommentResp.builder()
                .id(id)
                .author(author)
                .content(content.getContent())
                .createdAt(getCreatedAt())
                .modifiedAt(getModifiedAt())
                .build();
    }

    public static Comment from(final CommentWriteReq req, final Password encodedPassword) {
        return Comment.builder()
                .author(req.author())
                .content(new Content(req.content()))
                .password(encodedPassword)
                .build();
    }
}
