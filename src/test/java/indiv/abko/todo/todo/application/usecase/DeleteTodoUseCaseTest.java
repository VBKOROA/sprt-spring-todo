package indiv.abko.todo.todo.application.usecase;

import indiv.abko.todo.global.exception.BusinessException;
import indiv.abko.todo.todo.application.port.in.command.DeleteTodoCommand;
import indiv.abko.todo.todo.domain.port.out.PasswordDecoder;
import indiv.abko.todo.todo.domain.port.out.PasswordEncoder;
import indiv.abko.todo.todo.domain.port.out.TodoRepository;
import indiv.abko.todo.todo.domain.Todo;
import indiv.abko.todo.todo.domain.vo.PasswordVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteTodoUseCaseTest {

    @InjectMocks
    private DeleteTodoUseCase deleteTodoUseCase;

    @Mock
    private PasswordDecoder passwordDecoder;

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("할일 삭제 - 성공")
    void deleteTodo_success() {
        // given
        DeleteTodoCommand command = new DeleteTodoCommand(1L, "encodedPassword");
        String decodedPassword = "password";
        PasswordVO passwordVO = new PasswordVO("encodedPasswordValue"); // 실제 인코딩된 값
        Todo todo = Todo.builder().password(passwordVO).build();

        given(passwordDecoder.decode(command.encodedPassword())).willReturn(decodedPassword);
        given(todoRepository.findSummary(command.id())).willReturn(Optional.of(todo));
        given(passwordEncoder.matches(decodedPassword, passwordVO)).willReturn(true);

        // when
        deleteTodoUseCase.execute(command);

        // then
        verify(todoRepository).delete(todo);
    }

    @Test
    @DisplayName("할일 삭제 - 실패: 할일을 찾을 수 없음")
    void deleteTodo_fail_todoNotFound() {
        // given
        DeleteTodoCommand command = new DeleteTodoCommand(1L, "encodedPassword");
        given(passwordDecoder.decode(command.encodedPassword())).willReturn("password");
        given(todoRepository.findSummary(command.id())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> deleteTodoUseCase.execute(command))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("할일 삭제 - 실패: 비밀번호 불일치")
    void 삭제시_비밀번호가_일치하지_않으면_예외가_발생해야한다() {
        // given
        DeleteTodoCommand command = new DeleteTodoCommand(1L, "encodedPassword");
        String decodedPassword = "wrong_password";
        PasswordVO passwordVO = new PasswordVO("encodedPasswordValue");
        Todo todo = Todo.builder().password(passwordVO).build();

        given(passwordDecoder.decode(command.encodedPassword())).willReturn(decodedPassword);
        given(todoRepository.findSummary(command.id())).willReturn(Optional.of(todo));
        given(passwordEncoder.matches(decodedPassword, passwordVO)).willReturn(false);

        // when & then
        assertThatThrownBy(() -> deleteTodoUseCase.execute(command))
                .isInstanceOf(BusinessException.class);
    }
}