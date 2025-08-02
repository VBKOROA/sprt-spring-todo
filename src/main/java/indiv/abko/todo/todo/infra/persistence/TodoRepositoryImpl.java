package indiv.abko.todo.todo.infra.persistence;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import indiv.abko.todo.todo.domain.repository.TodoRepositoryCustom;
import indiv.abko.todo.todo.presentation.rest.dto.todo.TodoSearchCondition;
import indiv.abko.todo.todo.domain.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

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
                        like(condition.author(), todo.author),
                        like(condition.title(), todo.title.title),
                        like(condition.content(), todo.content.content)
                )
                .orderBy(getOrderBy(condition.orderBy()))
                .fetch();
    }

    private BooleanExpression like(String value, StringPath path) {
        return StringUtils.hasText(value) ?
                path.like("%" + value + "%") : null;
    }

    private OrderSpecifier<?> getOrderBy(String sort) {
        var sortCondition = parseValidSortString(sort);
        if (sortCondition.isEmpty()) {
            return DEFAULT_ORDER;
        }
        return createOrderSpecifierFrom(sortCondition.get());
    }

    private OrderSpecifier<?> createOrderSpecifierFrom(String[] sortCondition) {
        String property = sortCondition[PROPERTY_IDX];
        Order direction = "desc".equalsIgnoreCase(sortCondition[ORDER_IDX]) ?
                Order.DESC : Order.ASC;
        ComparableExpressionBase<?> path = getPath(property);
        return new OrderSpecifier<>(direction, path);
    }

    private Optional<String[]> parseValidSortString(String sort) {
        if (StringUtils.hasText(sort)) {
            String[] sortCondition = sort.split(ORDER_SEPARATOR);
            if (sortCondition.length == VALID_SORT_CONDITION_LENGTH) {
                return Optional.of(sortCondition);
            }
        }
        return Optional.empty();
    }

    private ComparableExpressionBase<?> getPath(String property) {
        return switch (property) {
            case "id" -> todo.id;
            case "title" -> todo.title.title;
            case "content" -> todo.content.content;
            case "author" -> todo.author;
            case "createdAt" -> todo.createdAt;
            default -> todo.modifiedAt;
        };
    }
}
