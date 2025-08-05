package indiv.abko.todo.todo.adapter.out.persistence;

import indiv.abko.todo.todo.adapter.out.persistence.entity.TodoJpaEntity;
import indiv.abko.todo.todo.adapter.out.persistence.mapper.TodoEntityMapper;
import indiv.abko.todo.todo.domain.SearchTodosCriteria;
import indiv.abko.todo.todo.domain.port.out.TodoRepository;
import indiv.abko.todo.todo.domain.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryAdapter implements TodoRepository {
    private final TodoJpaRepository todoJpaRepository;
    private final TodoQDSLRepository todoQDSLRepository;
    private final TodoEntityMapper todoEntityMapper;

    public Optional<Todo> findAggregate(final Long id) {
        return todoJpaRepository.findByIdWithComments(id)
                .map(todoEntityMapper::toAggregate);
    }

    @Override
    public List<Todo> searchSummaries(final SearchTodosCriteria searchCriteria) {
        final var todoEntities = todoQDSLRepository.search(searchCriteria);
        return todoEntities.stream().map(todoEntityMapper::toSummary).toList();
    }

    @Override
    public Todo save(final Todo todo) {
        final TodoJpaEntity todoEntity = todoJpaRepository.save(todoEntityMapper.toEntity(todo));
        return todoEntityMapper.toSummary(todoEntity);
    }

    @Override
    public Todo saveComment(final Todo todo) {
        final TodoJpaEntity todoEntity = todoJpaRepository.save(todoEntityMapper.toEntity(todo));
        return todoEntityMapper.toAggregate(todoEntity);
    }

    @Override
    public Optional<Todo> findSummary(final Long id) {
        return todoJpaRepository.findById(id).map(todoEntityMapper::toSummary);
    }

    @Override
    public void delete(final Todo todo) {
        final TodoJpaEntity todoEntity = todoEntityMapper.toEntity(todo);
        todoJpaRepository.delete(todoEntity);
    }
}
