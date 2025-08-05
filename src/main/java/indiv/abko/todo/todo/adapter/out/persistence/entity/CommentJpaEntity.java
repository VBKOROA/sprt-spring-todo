package indiv.abko.todo.todo.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
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
