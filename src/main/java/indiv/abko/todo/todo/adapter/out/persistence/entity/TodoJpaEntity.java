package indiv.abko.todo.todo.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "todo")
public class TodoJpaEntity extends BaseTimeJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // 일정 제목

    private String content; // 일정 내용

    private String author; // 작성자

    private String password; // 비밀번호

    // Todo 엔티티와 댓글 엔티티 간의 연관관계 설정
    @Builder.Default
    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentJpaEntity> comments = new ArrayList<>(); // 댓글 목록
}
