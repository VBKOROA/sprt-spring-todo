package indiv.abko.todo.todo.repository;

import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.StringUtils;
import indiv.abko.todo.todo.entity.Todo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoSortBuilder {
    private static final int SORT_CONDITION_LENGTH_SHOULD = 2;
    private static final String SORT_STRING_SEPARATOR = "_";
    private static final int FIELD_NAME_INDEX = 0;
    private static final int DIRECTION_INDEX = 1;

    /**
     * 주어진 검색 조건에 따라 정렬 객체를 생성한다.
     * 
     * @param orderString 정렬 조건을 포함하는 문자열.
     *                  - "필드명_정렬방향" 형식이어야 한다.
     *                  예: "modifiedAt_desc" 또는 "title_asc"
     * @return 유효한 정렬 조건이 제공된 경우 해당 조건에 따른 {@link Sort} 객체를 반환하며
     *         그렇지 않은 경우 기본적으로 modifiedAt을 기준으로 내림차순 정렬된 Sort 객체를 반환한다.
     */
    public static Sort buildWith(String orderString) {
        var defaultSort = Sort.by(Direction.DESC, Todo.Fields.modifiedAt.toString());

        if (StringUtils.hasText(orderString) == false) {
            return defaultSort;
        }

        return extractSortOrder(orderString).orElse(defaultSort);
    }

    private static Optional<Sort> extractSortOrder(String sortOrderString) {
        var orderCondition = sortOrderString.split(SORT_STRING_SEPARATOR);
        return getSortIfValid(orderCondition);
    }

    private static Optional<Sort> getSortIfValid(String[] sortCondition) {
        if (sortCondition.length != SORT_CONDITION_LENGTH_SHOULD) {
            return Optional.empty();
        }

        Optional<Direction> direction = Direction.fromOptionalString(sortCondition[DIRECTION_INDEX]);
        Optional<Todo.Fields> fieldName = Todo.toField(sortCondition[FIELD_NAME_INDEX]);

        final boolean isBothEmpty = direction.isEmpty() || fieldName.isEmpty();

        if (isBothEmpty) {
            return Optional.empty();    
        }

        return Optional.of(Sort.by(direction.get(), fieldName.get().toString()));
    }
}
