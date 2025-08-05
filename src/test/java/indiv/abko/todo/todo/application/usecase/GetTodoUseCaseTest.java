package indiv.abko.todo.todo.application.usecase;

import indiv.abko.todo.global.exception.BusinessException;
import indiv.abko.todo.todo.application.port.in.command.GetTodoCommand;
import indiv.abko.todo.todo.domain.port.out.TodoRepository;
import indiv.abko.todo.todo.domain.Todo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GetTodoUseCaseTest {

    @InjectMocks
    private GetTodoUseCase getTodoUseCase;

    @Mock
    private TodoRepository todoRepository;

    @Test
    @DisplayName("할일 조회 - 성공")
    void getTodo_success() {
        // given
        GetTodoCommand command = new GetTodoCommand(1L);
        Todo todo = Todo.builder().id(1L).build();
        given(todoRepository.findAggregate(command.id())).willReturn(Optional.of(todo));

        // when
        Todo result = getTodoUseCase.execute(command);

        // then
        verify(todoRepository).findAggregate(command.id());
        assertThat(result).isEqualTo(todo);
    }

    @Test
    @DisplayName("할일 조회 - 실패: 할일을 찾을 수 없음")
    void 조회할_할일을_찾지못하면_예외가_발생해야한다() {
        // given
        GetTodoCommand command = new GetTodoCommand(1L);
        given(todoRepository.findAggregate(command.id())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> getTodoUseCase.execute(command))
                .isInstanceOf(BusinessException.class);
    }
}