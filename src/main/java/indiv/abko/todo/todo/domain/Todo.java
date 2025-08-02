package indiv.abko.todo.todo.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import indiv.abko.todo.todo.domain.vo.Content;
import indiv.abko.todo.todo.presentation.rest.dto.comment.CommentResp;
import indiv.abko.todo.todo.presentation.rest.dto.todo.TodoCreateReq;
import indiv.abko.todo.todo.presentation.rest.dto.todo.TodoResp;
import indiv.abko.todo.todo.presentation.rest.dto.todo.TodoWithCommentsResp;
import indiv.abko.todo.todo.domain.vo.Password;
import indiv.abko.todo.todo.domain.vo.TodoTitle;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import indiv.abko.todo.todo.presentation.exception.BusinessException;
import indiv.abko.todo.todo.presentation.exception.ExceptionEnum;
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

    @Embedded
    private TodoTitle title; // 일정 제목

    @Embedded
    private Content content; // 일정 내용

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
    public Todo(TodoTitle title, Content content, String author, Password password) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.password = password;
    }

    public void updatePresented(String title, String author) {
        if (title != null) {
            this.title = new TodoTitle(title);
        }

        if (author != null) {
            this.author = author;
        }
    }

    public void addComment(Comment comment) {
        if (comments.size() == COMMENT_LIMIT) {
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

    public static Todo from(TodoCreateReq dto, Password encryptedPassword) {
        return Todo.builder()
                .title(new TodoTitle(dto.title()))
                .author(dto.author())
                .content(new Content(dto.content()))
                .password(encryptedPassword)
                .build();
    }

    public TodoResp toTodoResp() {
        return TodoResp.builder()
                .id(id)
                .title(title.getTitle())
                .content(content.getContent())
                .author(author)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .build();
    }

    public TodoWithCommentsResp toTodoWithCommentsResp() {
        TodoResp todoResp = toTodoResp();
        List<CommentResp> commentResps = toCommentResps();
        return new TodoWithCommentsResp(todoResp, commentResps);
    }

    private List<CommentResp> toCommentResps() {
        return comments.stream()
                .map(Comment::toCommentResp)
                .toList();
    }

    public Comment getLastComment() {
        final int lastIdx = comments.size() - 1;
        return comments.get(lastIdx);
    }
}
