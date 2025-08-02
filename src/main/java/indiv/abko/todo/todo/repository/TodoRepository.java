package indiv.abko.todo.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import indiv.abko.todo.todo.domain.Todo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface TodoRepository extends JpaRepository<Todo, Long>, TodoRepositoryCustom {
    @Query("""
        SELECT t FROM Todo t LEFT JOIN FETCH t.comments c
        WHERE t.id = :id
    """)
    Optional<Todo> findByIdWithComments(@Param("id") Long id);
}
