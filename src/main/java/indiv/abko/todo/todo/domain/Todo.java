package indiv.abko.todo.todo.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import indiv.abko.todo.todo.domain.port.out.PasswordEncoder;
import indiv.abko.todo.todo.domain.exception.TodoExceptionEnum;
import indiv.abko.todo.todo.domain.vo.ContentVO;
import indiv.abko.todo.todo.domain.vo.PasswordVO;
import indiv.abko.todo.todo.domain.vo.TodoTitleVO;
import indiv.abko.todo.global.exception.BusinessException;
import lombok.*;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Todo {
    private static final int COMMENT_LIMIT = 10;

    private Long id;
    private TodoTitleVO title; // 일정 제목
    private ContentVO content; // 일정 내용
    private String author; // 작성자
    private PasswordVO password; // 비밀번호
    @Builder.Default
    private List<Comment> comments = new ArrayList<>(); // 댓글 목록
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public void initCommentsViaRepository(List<Comment> comments) {
        this.comments = new ArrayList<>(comments);
    }

    public void updatePresented(final String title, final String author) {
        if (title != null) {
            this.title = new TodoTitleVO(title);
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

    public void shouldHaveAuth(final String rawPassword, final PasswordEncoder passwordEncoder) {
        if(password.matches(rawPassword, passwordEncoder) == false) {
            throw new BusinessException(TodoExceptionEnum.TODO_PERMISSION_DENIED);
        }
    }
}
