package indiv.abko.todo.todo.infra.persistence.entity;

import indiv.abko.todo.todo.domain.Todo;
import indiv.abko.todo.todo.domain.common.BaseTimeEntity;
import indiv.abko.todo.todo.domain.vo.Content;
import indiv.abko.todo.todo.domain.vo.Password;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "comment")
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
    private TodoJpaEntity todo;
}
