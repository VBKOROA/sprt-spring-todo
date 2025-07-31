package indiv.abko.todo.todo.repository;

import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.StringUtils;
import indiv.abko.todo.todo.dto.TodoSearchCondition;
import indiv.abko.todo.todo.entity.Todo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoSortBuilder {
    /**
     * 주어진 검색 조건에 따라 정렬 객체를 생성한다.
     * 
     * @param condition 정렬 조건을 포함하는 {@link TodoSearchCondition} 객체
     *                  - `orderBy` 필드는 "필드명_정렬방향" 형식이어야 한다.
     *                  예: "modifiedAt_desc" 또는 "title_asc"
     * @return 유효한 정렬 조건이 제공된 경우 해당 조건에 따른 {@link Sort} 객체를 반환하며
     *         그렇지 않은 경우 기본적으로 modifiedAt을 기준으로 내림차순 정렬된 Sort 객체를 반환한다.
     */
    public static Sort buildWith(TodoSearchCondition condition) {
        if (StringUtils.hasText(condition.orderBy())) {
            var orderCondition = condition.orderBy().split("_");
            var sort = getSortIfValid(orderCondition);
            if(sort.isPresent()) {
                return sort.get();
            }
        }

        // orderBy가 정상적이지 않다면
        return Sort.by(Direction.DESC, Todo.Fields.modifiedAt.toString());
    }

    private static Optional<Sort> getSortIfValid(String[] orderCondition) {
        if (orderCondition.length == 2) {
            Optional<Direction> dir = getDirection(orderCondition[1]);
            Optional<Todo.Fields> fieldName = Todo.toField(orderCondition[0]);

            if (dir.isPresent() && fieldName.isPresent()) {
                return Optional.of(Sort.by(dir.get(), fieldName.get().toString()));
            }
        }
        return Optional.empty();
    }

    private static Optional<Direction> getDirection(String directionString) {
        return Direction.fromOptionalString(directionString);
    }
}
