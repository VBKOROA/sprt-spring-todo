package indiv.abko.todo.todo.infra.persistence;

import indiv.abko.todo.todo.adapter.in.rest.dto.todo.TodoSearchCondition;
import indiv.abko.todo.todo.domain.Todo;
import indiv.abko.todo.todo.domain.repository.TodoRepository;
import indiv.abko.todo.todo.infra.persistence.entity.TodoJpaEntity;
import indiv.abko.todo.todo.infra.persistence.mapper.TodoEntityMapper;
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
    public List<Todo> searchSummaries(final TodoSearchCondition condition) {
        final var todoEntities = todoQDSLRepository.search(condition);
        return todoEntities.stream().map(todoEntityMapper::toSummary).toList();
    }

    @Override
    public Todo save(final Todo todo) {
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
