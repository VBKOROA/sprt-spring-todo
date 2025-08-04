package indiv.abko.todo.todo.domain;

import indiv.abko.todo.todo.domain.common.BaseTimeEntity;
import indiv.abko.todo.todo.domain.vo.Content;
import indiv.abko.todo.todo.domain.vo.Password;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment extends BaseTimeEntity {
    private Long id;
    private Content content;
    private String author;
    private Password password;
    private Todo todo;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public void atTodo(final Todo todo) {
        this.todo = todo;
    }
}
