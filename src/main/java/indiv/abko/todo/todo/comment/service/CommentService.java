package indiv.abko.todo.todo.comment.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import indiv.abko.todo.global.exception.BusinessException;
import indiv.abko.todo.global.exception.ExceptionEnum;
import indiv.abko.todo.todo.comment.dto.CommentResp;
import indiv.abko.todo.todo.comment.dto.CommentWriteReq;
import indiv.abko.todo.todo.comment.entity.Comment;
import indiv.abko.todo.todo.comment.mapper.CommentMapper;
import indiv.abko.todo.todo.comment.repository.CommentRepository;
import indiv.abko.todo.todo.entity.Todo;
import indiv.abko.todo.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentMapper commentMapper;

    /**
     * 주어진 Todo 엔티티에 연결된 댓글 응답 DTO 목록을 반환한다.
     *
     * @param todo 댓글을 조회할 대상 {@link Todo} 엔티티
     * @return 해당 Todo의 댓글을 나타내는 {@link CommentResp} 객체 리스트
     */
    public List<CommentResp> getComments(final Todo todo) {
        return todo.getComments().stream()
                .map(commentMapper::toCommentResp)
                .toList();
    }
}
