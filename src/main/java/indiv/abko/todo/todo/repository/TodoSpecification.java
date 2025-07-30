package indiv.abko.todo.todo.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import indiv.abko.todo.todo.entity.Todo;

public class TodoSpecification {
    public static Specification<Todo> authorLike(final String author) {
        return (root, query, builder) -> {
            if(StringUtils.hasText(author) == false) {
                // 항상 true 반환
                return builder.conjunction();
            }
            return builder.like(root.get(Todo.Fields.author), toPatternString(author));
        };
    }

    public static Specification<Todo> titleLike(final String title) {
        return (root, query, builder) -> {
            if(StringUtils.hasText(title) == false) {
                // 항상 true 반환
                return builder.conjunction();
            }
            return builder.like(root.get(Todo.Fields.title), toPatternString(title));
        };
    }

    public static Specification<Todo> contentLike(final String content) {
        return (root, query, builder) -> {
            if(StringUtils.hasText(content) == false) {
                // 항상 true 반환
                return builder.conjunction();
            }
            return builder.like(root.get(Todo.Fields.content), toPatternString(content));
        };
    }

    private static String toPatternString(String str) {
        return "%" + str + "%";
    }
}
