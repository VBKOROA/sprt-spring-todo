package indiv.abko.todo.todo.application.usecase;

import indiv.abko.todo.global.exception.BusinessException;
import indiv.abko.todo.todo.application.port.in.command.UpdateTodoCommand;
import indiv.abko.todo.todo.domain.port.out.PasswordEncoder;
import indiv.abko.todo.todo.domain.port.out.TodoRepository;
import indiv.abko.todo.todo.domain.Todo;
import indiv.abko.todo.todo.domain.vo.PasswordVO;
import indiv.abko.todo.todo.domain.vo.TodoTitleVO;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UpdateTodoUseCaseTest {

    @InjectMocks
    private UpdateTodoUseCase updateTodoUseCase;

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("할일 수정 - 성공")
    void updateTodo_success() {
        // given
        UpdateTodoCommand command = new UpdateTodoCommand(1L, "updated title", "updated author", "password");
        PasswordVO passwordVO = new PasswordVO("encodedPassword");
        Todo todo = Todo.builder()
                .id(1L)
                .password(passwordVO)
                .title(new TodoTitleVO("original title"))
                .author("original author")
                .build();

        given(todoRepository.findAggregate(command.id())).willReturn(Optional.of(todo));
        given(passwordEncoder.matches(command.password(), passwordVO)).willReturn(true);
        given(todoRepository.save(any(Todo.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        Todo result = updateTodoUseCase.execute(command);

        // then
        verify(todoRepository).save(any(Todo.class));
        assertThat(result.getTitle().getTitle()).isEqualTo(command.title());
        assertThat(result.getAuthor()).isEqualTo(command.author());
    }

    @Test
    @DisplayName("할일 수정 - 실패: 할일을 찾을 수 없음")
    void updateTodo_fail_todoNotFound() {
        // given
        UpdateTodoCommand command = new UpdateTodoCommand(1L, "updated title", "updated author", "password");
        given(todoRepository.findAggregate(command.id())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> updateTodoUseCase.execute(command))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("할일 수정 - 실패: 비밀번호 불일치")
    void 수정시_비밀번호가_일치하지_않으면_예외가_발생해야한다() {
        // given
        UpdateTodoCommand command = new UpdateTodoCommand(1L, "updated title", "updated author", "wrong_password");
        PasswordVO passwordVO = new PasswordVO("encodedPassword");
        Todo todo = Todo.builder().id(1L).password(passwordVO).build();

        given(todoRepository.findAggregate(command.id())).willReturn(Optional.of(todo));
        given(passwordEncoder.matches(command.password(), passwordVO)).willReturn(false);

        // when & then
        assertThatThrownBy(() -> updateTodoUseCase.execute(command))
                .isInstanceOf(BusinessException.class);
    }
}