package indiv.abko.todo.todo.repository;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import indiv.abko.todo.todo.entity.Todo;

public class TodoSortBuilderTest {
    @Test
    @DisplayName("정렬 스트링 파싱 성공")
    void 유효한정렬스트링이면_sort를반환한다() {
        // Given
        final String orderString = "id_asc";
        
        // When
        var sort = TodoSortBuilder.buildWith(orderString);
        var order = sort.getOrderFor(Todo.Fields.id.toString());

        // Then
        assertThat(order).isNotNull()
            .isEqualTo(Order.asc(Todo.Fields.id.toString()));
    }

    @Test
    @DisplayName("정렬 스트링 파싱 실패")
    void 유효하지않은정렬스트링이면_기본Sort를반환한다() {
        // Given
        final String orderString = "invalid_order";
        final Sort sortShouldBe = Sort.by(Direction.DESC, Todo.Fields.modifiedAt.toString());
        final Sort sortShouldntBe = Sort.by(Direction.ASC, Todo.Fields.id.toString());

        // When
        var sort = TodoSortBuilder.buildWith(orderString);

        // Then
        assertThat(sort).isNotNull()
            .isEqualTo(sortShouldBe)
            .isNotEqualTo(sortShouldntBe);
    }
}
