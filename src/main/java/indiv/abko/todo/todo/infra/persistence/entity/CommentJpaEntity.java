package indiv.abko.todo.todo.infra.persistence.entity;

import indiv.abko.todo.todo.domain.Todo;
import indiv.abko.todo.todo.domain.common.BaseTimeEntity;
import indiv.abko.todo.todo.domain.vo.Content;
import indiv.abko.todo.todo.domain.vo.Password;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CommentJpaEntity extends BaseTimeJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 댓글 내용
    private String content;

    // 작성자명
    private String author;

    // 비밀번호
    private String password;

    // Todo 엔티티와의 연관관계
    @ManyToOne
    private Todo todo;

    @Builder
    public CommentJpaEntity(final String content, final String author, final String password) {
        this.content = content;
        this.author = author;
        this.password = password;
    }
}
