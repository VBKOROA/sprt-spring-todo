package indiv.abko.todo.todo.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import indiv.abko.todo.global.util.Encrypt;
import indiv.abko.todo.todo.vo.Password;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import indiv.abko.todo.global.exception.BusinessException;
import indiv.abko.todo.global.exception.ExceptionEnum;
import indiv.abko.todo.todo.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Entity
@Getter
@NoArgsConstructor
@FieldNameConstants(asEnum = true)
@EntityListeners(AuditingEntityListener.class)
public class Todo {
    private static final int COMMENT_LIMIT = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // 일정 제목

    private String content; // 일정 내용

    private String author; // 작성자

    @Embedded
    private Password password; // 비밀번호

    // Todo 엔티티와 댓글 엔티티 간의 연관관계 설정
    @OneToMany(mappedBy = "todo", cascade = {CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>(); // 댓글 목록

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Builder
    public Todo(String title, String content, String author, Password password) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.password = password;
    }

    public void updatePresented(String title, String author) {
        if(title != null) {
            this.title = title;
        }

        if(author != null) {
            this.author = author;
        }
    }

    public void addComment(Comment comment) {
        if(comments.size() == COMMENT_LIMIT) {
            throw new BusinessException(ExceptionEnum.COMMENT_LIMIT_EXCEEDED);
        }
        comments.add(comment);
        comment.atTodo(this);
    }
    
    public static Optional<Todo.Fields> toField(String fieldName) {
        try {
            var field = Todo.Fields.valueOf(fieldName);
            return Optional.of(field);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
