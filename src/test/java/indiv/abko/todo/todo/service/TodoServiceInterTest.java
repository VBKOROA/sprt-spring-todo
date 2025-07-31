package indiv.abko.todo.todo.service;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import indiv.abko.todo.todo.dto.TodoSearchCondition;
import indiv.abko.todo.todo.entity.Todo;
import indiv.abko.todo.todo.repository.TodoRepository;

@SpringBootTest
@Transactional
public class TodoServiceInterTest {
    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoRepository todoRepository;

    @Test
    @DisplayName("Todo를 title로 검색할 수 있다.")
    void beforeEach() {
        // Given
        var todo1 = Todo.builder()
            .title("테스트")
            .author("테스트")
            .content("테스트")
            .password("null")
            .build();

        var todo2 = Todo.builder()
            .title("감스트")
            .author("테스트")
            .content("테스트")
            .password("null")
            .build();

        var todo3 = Todo.builder()
            .title("스프")
            .author("테스트")
            .content("테스트")
            .password("null")
            .build();

        Long id1 = todoRepository.save(todo1).getId();
        Long id2 =  todoRepository.save(todo2).getId();
        Long id3 =  todoRepository.save(todo3).getId();

        List<Long> ids = List.of(id3, id2, id1);

        TodoSearchCondition condition1 = new TodoSearchCondition(null, "테스트", null, null);
        TodoSearchCondition condition2 = new TodoSearchCondition(null, "스트", null, null);
        TodoSearchCondition condition3 = new TodoSearchCondition(null, "스", null, null);
        TodoSearchCondition condition4 = new TodoSearchCondition(null, "test", null, null);

        // When
        var result1 = todoService.fetchFilteredTodos(condition1);
        var result2 = todoService.fetchFilteredTodos(condition2);
        var result3 = todoService.fetchFilteredTodos(condition3);
        var result4 = todoService.fetchFilteredTodos(condition4);

        // Then
        assertThat(result1).isNotNull();
        assertThat(result2).isNotNull();
        assertThat(result3).isNotNull();
        assertThat(result4).isNotNull();

        assertThat(result1.todos().size()).isEqualTo(1);
        assertThat(result2.todos().size()).isEqualTo(2);
        assertThat(result3.todos().size()).isEqualTo(3);
        assertThat(result4.todos().size()).isZero();

        assertThat(result1.todos().get(0).id()).isEqualTo(id1);

        assertThat(result2.todos().get(0).id()).isEqualTo(id2);
        assertThat(result2.todos().get(1).id()).isEqualTo(id1);

        IntStream.rangeClosed(0, 2)
            .forEach(i -> {
                assertThat(result3.todos().get(i).id()).isEqualTo(ids.get(i));
            });
    }
}
