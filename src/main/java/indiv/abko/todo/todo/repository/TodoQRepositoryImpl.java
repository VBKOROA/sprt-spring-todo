package indiv.abko.todo.todo.repository;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import indiv.abko.todo.todo.dto.TodoSearchCondition;
import indiv.abko.todo.todo.entity.QTodo;
import indiv.abko.todo.todo.entity.Todo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TodoQRepositoryImpl implements TodoQRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 제공된 검색 조건에 따라 필터링된 할 일 목록을 가져온다.
     * 할 일 목록은 수정 날짜를 기준으로 내림차순으로 정렬한다.
     *
     * @param condition 작성자와 같은 필터를 포함하는 검색 조건
     * @return 필터링되고 매핑된 할 일 목록을 포함하는 {@link Todo} 목록
     */
    @Override
    public List<Todo> fetchFilteredTodos(TodoSearchCondition condition) {
        var qTodo = QTodo.todo;
        var query = queryFactory.selectFrom(qTodo)
            .orderBy(qTodo.modifiedAt.desc());

        if(condition.author() != null) {
            query = query.where(qTodo.author.eq(condition.author()));
        }

        return query.fetch();
    }
}
