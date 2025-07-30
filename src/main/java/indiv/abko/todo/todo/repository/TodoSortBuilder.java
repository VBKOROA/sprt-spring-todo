package indiv.abko.todo.todo.repository;

import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.StringUtils;
import indiv.abko.todo.global.entity.BaseTimeEntity;
import indiv.abko.todo.todo.dto.TodoSearchCondition;
import indiv.abko.todo.todo.entity.Todo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoSortBuilder {
    public static Sort buildWith(TodoSearchCondition condition) {
        if (StringUtils.hasText(condition.orderBy())) {
            var orderCondition = condition.orderBy().split("_");
            var sort = getSortIfValid(orderCondition);
            if(sort.isPresent()) {
                return sort.get();
            }
        }

        // orderBy가 정상적이지 않다면
        return Sort.by(Direction.ASC, Todo.Fields.id);
    }

    private static Optional<Sort> getSortIfValid(String[] orderCondition) {
        if (orderCondition.length == 2) {
            Optional<Direction> dir = getDirection(orderCondition[1]);
            Optional<String> fieldName = getFieldName(orderCondition[0]);

            if (dir.isPresent() && fieldName.isPresent()) {
                return Optional.of(Sort.by(dir.get(), fieldName.get()));
            }
        }
        return Optional.empty();
    }

    private static Optional<String> getFieldName(String fieldString) {
        return Optional.ofNullable(switch (fieldString) {
            case Todo.Fields.title -> Todo.Fields.title;
            case Todo.Fields.author -> Todo.Fields.author;
            case Todo.Fields.content -> Todo.Fields.content;
            case BaseTimeEntity.Fields.modifiedAt -> BaseTimeEntity.Fields.modifiedAt;
            case BaseTimeEntity.Fields.createdAt -> BaseTimeEntity.Fields.createdAt;
            default -> null;
        });
    }

    private static Optional<Direction> getDirection(String directionString) {
        return Optional.ofNullable(switch (directionString) {
            case "asc" -> Direction.ASC;
            case "desc" -> Direction.DESC;
            default -> null;
        });
    }
}
