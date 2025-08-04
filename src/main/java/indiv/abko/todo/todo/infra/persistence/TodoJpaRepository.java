package indiv.abko.todo.todo.infra.persistence;

import indiv.abko.todo.todo.infra.persistence.entity.TodoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TodoJpaRepository extends JpaRepository<TodoJpaEntity,Long> {
    @Query("""
        SELECT t FROM TodoJpaEntity t LEFT JOIN FETCH t.comments c
        WHERE t.id = :id
    """)
    Optional<TodoJpaEntity> findByIdWithComments(@Param("id") Long id);}
