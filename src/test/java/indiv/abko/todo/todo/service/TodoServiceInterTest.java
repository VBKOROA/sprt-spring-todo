package indiv.abko.todo.todo.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import indiv.abko.todo.global.vo.Content;
import indiv.abko.todo.global.vo.Password;
import indiv.abko.todo.todo.entity.vo.TodoTitle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import indiv.abko.todo.todo.dto.TodoSearchCondition;
import indiv.abko.todo.todo.domain.Todo;
import indiv.abko.todo.todo.domain.repository.TodoRepository;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DisplayName("TodoService 통합 테스트")
public class TodoServiceInterTest {
    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoRepository todoRepository;

    @Nested
    @DisplayName("Todo를 title로 검색할 수 있다.")
    class SearchByTitle {
        private List<Todo> savedTodos = new ArrayList<>();

        @BeforeEach
        void setUp() {
            todoRepository.deleteAll();

            List<Todo> todos = new ArrayList<>();

            todos.add(Todo.builder()
                    .title(new TodoTitle("테스트"))
                    .author("테스트")
                    .content(new Content("테스트"))
                    .password(new Password("null"))
                    .build());

            todos.add(Todo.builder()
                    .title(new TodoTitle("감스트"))
                    .author("테스트")
                    .content(new Content("테스트"))
                    .password(new Password("null"))
                    .build());

            todos.add(Todo.builder()
                    .title(new TodoTitle("스프"))
                    .author("테스트")
                    .content(new Content("테스트"))
                    .password(new Password("null"))
                    .build());

            savedTodos = todoRepository.saveAll(todos);
        }

        @Test
        @DisplayName("가장 정확한 키워드로 1건 조회하기")
        void 정확한_키워드로_조회() {
            // Given
            TodoSearchCondition condition = new TodoSearchCondition(null, "테스트", null, null);

            // When
            var result = todoService.fetchFilteredTodos(condition);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.todos().size()).isEqualTo(1);
            assertThat(result.todos().get(0).id()).isEqualTo(savedTodos.get(0).getId());
        }

        @Test
        @DisplayName("부분 일치 키워드로 2건 조회하기")
        void 부분_일치_키워드로_조회_1() {
            // Given
            TodoSearchCondition condition2 = new TodoSearchCondition(null, "스트", null, null);

            // When
            var result2 = todoService.fetchFilteredTodos(condition2);

            // Then
            assertThat(result2).isNotNull();
            assertThat(result2.todos().size()).isEqualTo(2);
            assertThat(result2.todos().get(0).id()).isEqualTo(savedTodos.get(1).getId());
            assertThat(result2.todos().get(1).id()).isEqualTo(savedTodos.get(0).getId());
        }

        @Test
        @DisplayName("부분 일치 키워드로 3건 조회하기")
        void 부분_일치_키워드로_조회_2() {
            // Given
            TodoSearchCondition condition3 = new TodoSearchCondition(null, "스", null, null);

            // When
            var result3 = todoService.fetchFilteredTodos(condition3);

            // Then
            assertThat(result3).isNotNull();
            assertThat(result3.todos().size()).isEqualTo(3);
            IntStream.rangeClosed(0, 2)
                    .forEach(i ->
                            assertThat(result3.todos().get(i).id()).isEqualTo(savedTodos.get(savedTodos.size() - 1 - i).getId())
                    );
        }

        @Test
        @DisplayName("없는 키워드로 조회하기")
        void 없는_키워드로_조회() {
            // Given
            TodoSearchCondition condition4 = new TodoSearchCondition(null, "test", null, null);

            // When
            var result4 = todoService.fetchFilteredTodos(condition4);

            // Then
            assertThat(result4).isNotNull();
            assertThat(result4.todos().size()).isZero();
        }
    }
}
