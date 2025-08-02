package indiv.abko.todo.todo.domain;

import java.time.LocalDateTime;

import indiv.abko.todo.global.vo.Content;
import indiv.abko.todo.global.vo.Password;
import indiv.abko.todo.todo.comment.dto.CommentResp;
import indiv.abko.todo.todo.comment.dto.CommentWriteReq;
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
@EntityListeners(AuditingEntityListener.class)
public class Comment {
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

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Builder
    public Comment(Content content, String author, Password password) {
        this.content = content;
        this.author = author;
        this.password = password;
    }

    public void atTodo(Todo todo) {
        this.todo = todo;
    }

    public CommentResp toCommentResp() {
        return CommentResp.builder()
                .id(id)
                .author(author)
                .content(content.getContent())
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .build();
    }

    public static Comment from(CommentWriteReq req, Password encodedPassword) {
        return Comment.builder()
                .author(req.author())
                .content(new Content(req.content()))
                .password(encodedPassword)
                .build();
    }
}
