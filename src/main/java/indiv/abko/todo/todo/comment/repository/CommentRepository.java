package indiv.abko.todo.todo.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import indiv.abko.todo.todo.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
