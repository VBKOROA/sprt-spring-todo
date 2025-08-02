package indiv.abko.todo.todo.infra.persistence;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQueryFactory;
import indiv.abko.todo.todo.domain.Todo;
import indiv.abko.todo.todo.domain.repository.TodoRepositoryCustom;
import indiv.abko.todo.todo.presentation.rest.dto.todo.TodoSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static indiv.abko.todo.todo.domain.QTodo.todo;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepositoryCustom {
    private static final String ORDER_SEPARATOR = "_";
    private static final int PROPERTY_IDX = 0;
    private static final int ORDER_IDX = 1;
    private static final int VALID_SORT_CONDITION_LENGTH = 2;
    private static final OrderSpecifier<?> DEFAULT_ORDER = new OrderSpecifier<>(Order.DESC, todo.modifiedAt);

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
            return DEFAULT_ORDER;
        }

        String[] sortCondition = sort.split(ORDER_SEPARATOR);
        if (sortCondition.length != VALID_SORT_CONDITION_LENGTH) {
            return DEFAULT_ORDER;
        }

        String property = sortCondition[PROPERTY_IDX];
        ComparableExpressionBase<?> path = getPath(property);

        if (path == null) {
            return DEFAULT_ORDER;
        }

        Order direction = "desc".equalsIgnoreCase(sortCondition[ORDER_IDX]) ?
                Order.DESC : Order.ASC;

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
            default -> null;
        };
    }
}