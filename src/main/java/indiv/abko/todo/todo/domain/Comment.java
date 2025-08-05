package indiv.abko.todo.todo.domain;

import indiv.abko.todo.todo.domain.vo.ContentVO;
import indiv.abko.todo.todo.domain.vo.PasswordVO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment {
    private Long id;
    private ContentVO content;
    private String author;
    private PasswordVO password;
    private Todo todo;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public void atTodo(final Todo todo) {
        this.todo = todo;
    }
}
