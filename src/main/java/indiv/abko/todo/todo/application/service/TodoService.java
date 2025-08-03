package indiv.abko.todo.todo.application.service;

import indiv.abko.todo.todo.application.mapper.TodoDomainMapper;
import indiv.abko.todo.todo.application.port.out.PasswordDecoder;
import indiv.abko.todo.todo.application.port.out.PasswordEncoder;
import indiv.abko.todo.todo.domain.vo.Password;
import indiv.abko.todo.todo.presentation.rest.dto.comment.CommentResp;
import indiv.abko.todo.todo.presentation.rest.dto.comment.CommentWriteReq;
import indiv.abko.todo.todo.domain.Comment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import indiv.abko.todo.todo.presentation.exception.BusinessException;
import indiv.abko.todo.todo.presentation.exception.ExceptionEnum;
import indiv.abko.todo.todo.presentation.rest.dto.todo.TodoCreateReq;
import indiv.abko.todo.todo.presentation.rest.dto.todo.TodoSearchCondition;
import indiv.abko.todo.todo.presentation.rest.dto.todo.TodoUpdateReq;
import indiv.abko.todo.todo.presentation.rest.dto.todo.TodoWithCommentsResp;
import indiv.abko.todo.todo.domain.Todo;
import indiv.abko.todo.todo.domain.repository.TodoRepository;
import indiv.abko.todo.todo.presentation.rest.dto.todo.TodoListResp;
import indiv.abko.todo.todo.presentation.rest.dto.todo.TodoResp;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepo;
    private final PasswordEncoder passwordEncoder;
    private final PasswordDecoder passwordDecoder;
    private final TodoDomainMapper todoDomainMapper;

    /**
     * 주어진 요청 데이터로 새로운 Todo 항목을 생성한다.
     *
     * @param todoReq 새 Todo의 정보를 담은 요청 객체
     * @return 생성된 Todo 정보를 담은 {@link TodoResp}
     */
    @Transactional
    public TodoResp create(final TodoCreateReq todoReq) {
        final Todo todo = todoDomainMapper.toTodo(todoReq);
        final Todo result = todoRepo.save(todo);
        return todoDomainMapper.toTodoResp(result);
    }

    /**
     * 주어진 ID에 해당하는 Todo와 그에 연결된 댓글 목록을 함께 조회한다.
     *
     * @param id 조회할 {@link Todo}의 ID
     * @return Todo 정보와 댓글 목록을 포함하는 {@link TodoWithCommentsResp} 객체
     * @throws BusinessException 해당 ID의 Todo가 존재하지 않을 경우 발생
     */
    @Transactional(readOnly = true)
    public TodoWithCommentsResp getTodoWithComments(final Long id) {
        final Todo todo = todoRepo.findByIdWithComments(id)
                .orElseThrow(() -> new BusinessException(ExceptionEnum.TODO_NOT_FOUND));
        return todoDomainMapper.toTodoWithCommentsResp(todo);
    }

    private Todo retrieveOrThrow(final Long id) {
        return todoRepo.findById(id)
                .orElseThrow(() -> new BusinessException(ExceptionEnum.TODO_NOT_FOUND));
    }

    /**
     * 주어진 조건에 따라 Todo 목록을 조회한다.
     *
     * @param condition Todo 조회 조건 객체
     * @return 조건에 맞는 {@link TodoListResp} 객체
     */
    @Transactional(readOnly = true)
    public TodoListResp fetchFilteredTodos(final TodoSearchCondition condition) {
        final var todos = todoRepo.search(condition);
        return new TodoListResp(todos.stream()
            .map(todoDomainMapper::toTodoResp)
            .toList());
    }

    /**
     * 주어진 ID에 해당하는 Todo 항목을 업데이트한다.
     * 
     * @param id        업데이트할 {@link Todo} 항목의 ID
     * @param updateReq 업데이트 요청 데이터
     * @return 업데이트된 Todo 항목의 응답 객체
     * @throws BusinessException 비밀번호가 일치하지 않아 권한이 없을 경우 발생
     */
    @Transactional
    public TodoResp updateTodo(final Long id, final TodoUpdateReq updateReq) {
        final Todo todo = retrieveOrThrow(id);
        shouldHaveAuth(todo, updateReq.password());
        todo.updatePresented(updateReq.title(), updateReq.author());
        return todoDomainMapper.toTodoResp(todo);
    }

    /**
     * 주어진 ID와 비밀번호를 사용하여 Todo를 삭제한다.
     * 
     * @param id       삭제할 {@link Todo}의 고유 ID
     * @param encodedPassword Todo 삭제를 위한 인증 비밀번호
     * @throws BusinessException - 주어진 ID에 해당하는 Todo가 존재하지 않을 경우
     *                           - 비밀번호가 일치하지 않을 경우
     */
    @Transactional
    public void deleteTodo(final Long id, final String encodedPassword) {
        final String decodedPassword = passwordDecoder.decode(encodedPassword);
        final Todo todo = retrieveOrThrow(id);
        shouldHaveAuth(todo, decodedPassword);
        todoRepo.delete(todo);
    }

    /**
     * 지정된 Todo 항목에 새로운 댓글을 저장한다.
     *
     * @param todoId 댓글이 추가될 {@link Todo} 항목의 ID
     * @param req    댓글의 내용, 작성자, 비밀번호를 포함하는 요청 객체
     * @return 생성된 댓글의 세부 정보를 포함하는 {@link CommentResp} 객체
     * @throws BusinessException Todo 항목을 찾을 수 없거나 댓글 제한을 초과한 경우
     */
    @Transactional
    public CommentResp addComment(final Long todoId, final CommentWriteReq req) {
        final Todo todo = retrieveOrThrow(todoId);
        final Password encodedPassword = passwordEncoder.encode(req.password());
        final Comment comment = Comment.from(req, encodedPassword);
        todo.addComment(comment);
        todoRepo.save(todo);
        final Comment savedComment = todo.getLastComment();
        return savedComment.toCommentResp();
    }

    private void shouldHaveAuth(final Todo todo, final String rawPassword) {
        if(passwordEncoder.matches(rawPassword, todo.getPassword()) == false) {
            throw new BusinessException(ExceptionEnum.TODO_PERMISSION_DENIED);
        }
    }
}
