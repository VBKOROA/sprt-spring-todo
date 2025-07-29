package indiv.abko.todo.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByAuthorContainingOrderByModifiedAtDesc(String author);

    List<Todo> findByAuthorOrderByModifiedAtDesc(String author);
}
