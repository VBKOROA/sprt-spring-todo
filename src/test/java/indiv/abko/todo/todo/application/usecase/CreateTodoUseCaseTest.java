package indiv.abko.todo.todo.application.usecase;

import indiv.abko.todo.todo.application.port.in.command.CreateTodoCommand;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateTodoUseCaseTest {

    @InjectMocks
    private CreateTodoUseCase createTodoUseCase;

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("할일 생성 - 성공")
    void 할일을_성공적으로_생성해야한다() {
        // given
        CreateTodoCommand command = new CreateTodoCommand("title", "content", "author", "password");
        PasswordVO encodedPassword = new PasswordVO("encodedPassword");

        given(passwordEncoder.encode(command.password())).willReturn(encodedPassword);
        given(todoRepository.save(any(Todo.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        Todo result = createTodoUseCase.execute(command);

        // then
        verify(todoRepository).save(any(Todo.class));
        assertThat(result.getAuthor()).isEqualTo(command.author());
        assertThat(result.getTitle().getTitle()).isEqualTo(command.title());
        assertThat(result.getContent().getContent()).isEqualTo(command.content());
        assertThat(result.getPassword()).isEqualTo(encodedPassword);
    }
}