package indiv.abko.todo.todo.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import indiv.abko.todo.todo.entity.Todo;

public class TodoSpecification {
    public static Specification<Todo> authorEquals(final String author) {
        return (root, query, builder) -> {
            if(StringUtils.hasText(author) == false) {
                // 항상 true 반환
                return builder.conjunction();
            }
            return builder.equal(root.get(Todo.Fields.author), author);
        };
    }
}
