package indiv.abko.todo.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import indiv.abko.todo.todo.entity.Todo;

import java.util.List;


public interface TodoRepository extends JpaRepository<Todo, Long>, TodoQRepository {
    List<Todo> findByAuthorOrderByModifiedAtDesc(String author);

    List<Todo> findByOrderByModifiedAtDesc();
}
