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
    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;

    /**
     * 지정된 Todo 항목에 새로운 댓글을 생성한다.
     * 
     * @param todoId 댓글이 추가될 {@link Todo} 항목의 ID
     * @param req    댓글의 내용, 작성자, 비밀번호를 포함하는 요청 객체
     * @return 생성된 댓글의 세부 정보를 포함하는 {@link CommentResp} 객체
     * @throws BusinessException Todo 항목을 찾을 수 없거나 댓글 제한을 초과한 경우
     */
    @Transactional
    public CommentResp createComment(Long todoId, CommentWriteReq req) {
        Todo todo = retrieveTodoOrThrow(todoId);
        Comment comment = commentMapper.toComment(req);
        todo.addComment(comment);
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toCommentResp(savedComment);
    }

    private Todo retrieveTodoOrThrow(Long todoId) {
        return todoRepository.findById(todoId)
            .orElseThrow(() -> new BusinessException(ExceptionEnum.TODO_NOT_FOUND));
    }

    /**
     * 주어진 Todo 엔티티에 연결된 댓글 응답 DTO 목록을 반환한다.
     *
     * @param todo 댓글을 조회할 대상 {@link Todo} 엔티티
     * @return 해당 Todo의 댓글을 나타내는 {@link CommentResp} 객체 리스트
     */
    public List<CommentResp> getComments(Todo todo) {
        return todo.getComments().stream()
                .map(commentMapper::toCommentResp)
                .toList();
    }
}
