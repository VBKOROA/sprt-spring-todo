package indiv.abko.todo.todo.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import indiv.abko.todo.todo.entity.Todo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

// 외부에서의 생성 차단
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoSpecBuilder {
    private Specification<Todo> spec = Specification.unrestricted();

    public static TodoSpecBuilder builder() {
        return new TodoSpecBuilder();
    }

    public TodoSpecBuilder authorLike(final String author) {
        addLikeSpecification(author, Todo.Fields.author);
        return this;
    }

    public TodoSpecBuilder titleLike(final String title) {
        addLikeSpecification(title, Todo.Fields.title);
        return this;
    }

    public TodoSpecBuilder contentLike(final String content) {
        addLikeSpecification(content, Todo.Fields.content);
        return this;
    }

    public Specification<Todo> build() {
        return spec;
    }

    private void addLikeSpecification(final String value, final Todo.Fields field) {
        if(StringUtils.hasText(value) == true) {
            spec = spec.and((root, query, cb) 
                -> cb.like(root.get(field.toString()), toPatternString(value)));
        }
    }

    private String toPatternString(final String str) {
        return "%" + str + "%";
    }
}
