package indiv.abko.todo.todo.application.usecase;

import indiv.abko.todo.todo.application.port.in.command.CreateTodoCommand;
import indiv.abko.todo.todo.application.port.out.TodoRepository;
import indiv.abko.todo.todo.domain.Todo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateTodoUseCaseTest {

    @InjectMocks
    private CreateTodoUseCase createTodoUseCase;

    @Mock
    private TodoRepository todoRepository;

    @Test
    @DisplayName("할일 생성 - 성공")
    void 할일을_성공적으로_생성해야한다() {
        // given
        CreateTodoCommand command = new CreateTodoCommand("title", "content", "author", "password");

        // when
        // execute 메소드는 생성된 todo를 반환하므로, save 호출 시 인자를 캡처하거나 할 필요 없이 반환값을 검증합니다.
        Todo result = createTodoUseCase.execute(command);

        // then
        verify(todoRepository).save(any(Todo.class));
        assertThat(result.getAuthor()).isEqualTo(command.author());
        assertThat(result.getTitle().getTitle()).isEqualTo(command.title());
        assertThat(result.getContent().getContent()).isEqualTo(command.content());
        // Password는 보통 직접 비교하지 않지만, 여기서는 로직 검증을 위해 추가
        assertThat(result.getPassword().getPassword()).isNotNull();
    }
}