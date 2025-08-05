package indiv.abko.todo.todo.domain;

import indiv.abko.todo.global.exception.BusinessException;
import indiv.abko.todo.todo.domain.exception.TodoExceptionEnum;
import indiv.abko.todo.todo.domain.vo.ContentVO;
import indiv.abko.todo.todo.domain.vo.PasswordVO;
import indiv.abko.todo.todo.domain.vo.TodoTitleVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TodoTest {

    @Test
    @DisplayName("댓글 추가 - 성공 케이스")
    void 댓글을성공적으로추가해야한다() {
        // given
        Todo todo = Todo.builder()
                .title(new TodoTitleVO("테스트 제목"))
                .content(new ContentVO("테스트 내용"))
                .password(new PasswordVO("password1234"))
                .author("user")
                .build();

        Comment comment = Comment.builder()
                .content(new ContentVO("테스트 댓글"))
                .author("commenter")
                .build();

        // when
        todo.addComment(comment);

        // then
        assertThat(todo.getComments()).hasSize(1);
        assertThat(todo.getComments().get(0)).isEqualTo(comment);
    }

    @Test
    @DisplayName("댓글 추가 - 실패 케이스: 최대 개수 초과")
    void 댓글최대개수제한을초과하면_예외가발생해야한다() {
        // given
        Todo todo = Todo.builder()
                .title(new TodoTitleVO("테스트 제목"))
                .content(new ContentVO("테스트 내용"))
                .password(new PasswordVO("password1234"))
                .author("user")
                .build();

        // 10개의 댓글을 미리 추가
        for (int i = 0; i < 10; i++) {
            todo.addComment(Comment.builder().content(new ContentVO("댓글 " + i)).author("user").build());
        }

        Comment newComment = Comment.builder()
                .content(new ContentVO("11번째 댓글"))
                .author("commenter")
                .build();

        // when & then
        assertThatThrownBy(() -> todo.addComment(newComment))
                .isInstanceOf(BusinessException.class)
                .satisfies(e -> assertThat(((BusinessException) e).getBusinessExceptionEnum())
                        .isEqualTo(TodoExceptionEnum.COMMENT_LIMIT_EXCEEDED));
    }
}