package indiv.abko.todo.todo.infra.persistence.entity;

import indiv.abko.todo.global.exception.BusinessException;
import indiv.abko.todo.todo.domain.Comment;
import indiv.abko.todo.todo.domain.common.BaseTimeEntity;
import indiv.abko.todo.todo.domain.exception.TodoExceptionEnum;
import indiv.abko.todo.todo.domain.vo.Content;
import indiv.abko.todo.todo.domain.vo.Password;
import indiv.abko.todo.todo.domain.vo.TodoTitle;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@FieldNameConstants(asEnum = true)
public class TodoJpaEntity extends BaseTimeJpaEntity {
    private static final int COMMENT_LIMIT = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // 일정 제목

    private String content; // 일정 내용

    private String author; // 작성자

    private String password; // 비밀번호

    // Todo 엔티티와 댓글 엔티티 간의 연관관계 설정
    @OneToMany(mappedBy = "todo", cascade = {CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>(); // 댓글 목록

    @Builder
    public TodoJpaEntity(final String title, final String content, final String author, final String password) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.password = password;
    }
}
