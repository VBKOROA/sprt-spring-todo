package indiv.abko.todo.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import indiv.abko.todo.todo.entity.Todo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface TodoRepository extends JpaRepository<Todo, Long>, JpaSpecificationExecutor<Todo> {
    @Query("""
        SELECT t FROM Todo t LEFT JOIN FETCH t.comments c
        WHERE t.id = :id
    """)
    Optional<Todo> findByIdWithComments(@Param("id") Long id);
}
