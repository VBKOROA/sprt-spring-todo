package indiv.abko.todo.todo.application.usecase;

import indiv.abko.todo.todo.application.port.in.command.SearchTodosCommand;
import indiv.abko.todo.todo.domain.SearchTodosCriteria;
import indiv.abko.todo.todo.domain.port.out.TodoRepository;
import indiv.abko.todo.todo.domain.Todo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SearchTodosUseCaseTest {

    @InjectMocks
    private SearchTodosUseCase searchTodosUseCase;

    @Mock
    private TodoRepository todoRepository;

    @Test
    @DisplayName("할일 목록 검색 - 성공")
    void 할일_목록을_성공적으로_검색해야한다() {
        // given
        SearchTodosCommand command = new SearchTodosCommand("createdAt", "title", "content", "author");
        SearchTodosCriteria criteria = command.toCriteria();
        List<Todo> todos = Collections.singletonList(Todo.builder().build());
        given(todoRepository.searchSummaries(criteria)).willReturn(todos);

        // when
        List<Todo> result = searchTodosUseCase.execute(command);

        // then
        verify(todoRepository).searchSummaries(criteria);
        assertThat(result).isEqualTo(todos);
    }
}