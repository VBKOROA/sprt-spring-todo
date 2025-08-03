package indiv.abko.todo.todo.domain;

import java.util.ArrayList;
import java.util.List;

import indiv.abko.todo.todo.domain.common.BaseTimeEntity;
import indiv.abko.todo.todo.domain.exception.TodoExceptionEnum;
import indiv.abko.todo.todo.domain.vo.Content;
import indiv.abko.todo.todo.domain.vo.Password;
import indiv.abko.todo.todo.domain.vo.TodoTitle;
import jakarta.persistence.*;
import indiv.abko.todo.global.exception.BusinessException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Entity
@Getter
@NoArgsConstructor
@FieldNameConstants(asEnum = true)
public class Todo extends BaseTimeEntity {
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

    @Builder
    public Todo(final TodoTitle title, final Content content, final String author, final Password password) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.password = password;
    }

    public void updatePresented(final String title, final String author) {
        if (title != null) {
            this.title = new TodoTitle(title);
        }

        if (author != null) {
            this.author = author;
        }
    }

    public void addComment(final Comment comment) {
        if (comments.size() == COMMENT_LIMIT) {
            throw new BusinessException(TodoExceptionEnum.COMMENT_LIMIT_EXCEEDED);
        }
        comments.add(comment);
        comment.atTodo(this);
    }

    public Comment getLastComment() {
        final int lastIdx = comments.size() - 1;
        return comments.get(lastIdx);
    }
}
