package indiv.abko.todo.todo.comment.entity;

import indiv.abko.todo.global.entity.BaseTimeEntity;
import indiv.abko.todo.todo.entity.Todo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseTimeEntity {
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
    public Comment(String content, String author, String password) {
        this.content = content;
        this.author = author;
        this.password = password;
    }
}
