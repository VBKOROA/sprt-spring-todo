package indiv.abko.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import indiv.abko.todo.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
