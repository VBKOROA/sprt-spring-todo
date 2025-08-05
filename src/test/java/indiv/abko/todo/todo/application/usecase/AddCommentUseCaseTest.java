package indiv.abko.todo.todo.application.usecase;

import indiv.abko.todo.global.exception.BusinessException;
import indiv.abko.todo.todo.application.port.in.command.AddCommentCommand;
import indiv.abko.todo.todo.domain.port.out.PasswordEncoder;
import indiv.abko.todo.todo.domain.port.out.TodoRepository;
import indiv.abko.todo.todo.domain.Comment;
import indiv.abko.todo.todo.domain.Todo;
import indiv.abko.todo.todo.domain.vo.PasswordVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddCommentUseCaseTest {

    @InjectMocks
    private AddCommentUseCase addCommentUseCase;

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("댓글 추가 - 성공")
    void addComment_success() {
        // given
        AddCommentCommand command = new AddCommentCommand(1L, "content", "author", "password");
        Todo todo = Todo.builder().id(1L).build();
        PasswordVO encodedPassword = new PasswordVO("encodedPassword");

        given(todoRepository.findAggregate(command.todoId())).willReturn(Optional.of(todo));
        given(passwordEncoder.encode(anyString())).willReturn(encodedPassword);
        given(todoRepository.saveComment(any(Todo.class))).willAnswer(invocation -> {
            // UseCase에서 comment를 추가한 todo 객체가 넘어오므로 그대로 반환
            return invocation.getArgument(0);
        });

        // when
        Comment result = addCommentUseCase.execute(command);

        // then
        verify(todoRepository).findAggregate(command.todoId());
        verify(todoRepository).saveComment(any(Todo.class));
        assertThat(result).isNotNull();
        assertThat(result.getAuthor()).isEqualTo(command.author());
        assertThat(result.getContent().getContent()).isEqualTo(command.content());
        assertThat(result.getPassword()).isEqualTo(encodedPassword);
    }

    @Test
    @DisplayName("댓글 추가 - 실패: 할일을 찾을 수 없음")
    void 할일을_찾지못하면_댓글추가시_예외가_발생해야한다() {
        // given
        AddCommentCommand command = new AddCommentCommand(1L, "content", "author", "password");
        given(todoRepository.findAggregate(command.todoId())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> addCommentUseCase.execute(command))
                .isInstanceOf(BusinessException.class);
    }
}