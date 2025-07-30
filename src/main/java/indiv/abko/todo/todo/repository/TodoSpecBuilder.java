package indiv.abko.todo.todo.repository;

import org.springframework.data.jpa.domain.Specification;
import indiv.abko.todo.todo.dto.TodoSearchCondition;
import indiv.abko.todo.todo.entity.Todo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

// 외부에서의 생성 차단
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoSpecBuilder {
    public static Specification<Todo> buildWith(TodoSearchCondition condition) {
        Specification<Todo> spec = TodoSpecification.authorEquals(condition.author());
        return spec;
    }
}
