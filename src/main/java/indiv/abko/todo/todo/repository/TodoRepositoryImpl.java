package indiv.abko.todo.todo.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQueryFactory;
import indiv.abko.todo.todo.dto.TodoSearchCondition;
import indiv.abko.todo.todo.entity.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static indiv.abko.todo.todo.entity.QTodo.todo;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Todo> search(TodoSearchCondition condition) {
        return queryFactory
                .selectFrom(todo)
                .where(
                        authorLike(condition.author()),
                        titleLike(condition.title()),
                        contentLike(condition.content())
                )
                .orderBy(getOrderBy(condition.orderBy()))
                .fetch();
    }

    private BooleanExpression authorLike(String author) {
        return StringUtils.hasText(author) ? todo.author.like("%" + author + "%") : null;
    }

    private BooleanExpression titleLike(String title) {
        return StringUtils.hasText(title) ? todo.title.title.like("%" + title + "%") : null;
    }

    private BooleanExpression contentLike(String content) {
        return StringUtils.hasText(content) ? todo.content.content.like("%" + content + "%") : null;
    }

    private OrderSpecifier<?> getOrderBy(String sort) {
        if (!StringUtils.hasText(sort)) {
            return new OrderSpecifier<>(Order.DESC, todo.modifiedAt);
        }

        String[] sortCondition = sort.split("_");
        if (sortCondition.length != 2) {
            return new OrderSpecifier<>(Order.DESC, todo.modifiedAt);
        }

        String property = sortCondition[0];
        Order direction = "desc".equalsIgnoreCase(sortCondition[1]) ? Order.DESC : Order.ASC;

        ComparableExpressionBase<?> path = getPath(property);

        return new OrderSpecifier<>(direction, path);
    }

    private ComparableExpressionBase<?> getPath(String property) {
        return switch (property) {
            case "id" -> todo.id;
            case "title" -> todo.title.title;
            case "content" -> todo.content.content;
            case "author" -> todo.author;
            case "createdAt" -> todo.createdAt;
            case "modifiedAt" -> todo.modifiedAt;
            default -> todo.modifiedAt;
        };
    }
}
