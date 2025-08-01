package indiv.abko.todo.todo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import indiv.abko.todo.global.exception.BusinessException;
import indiv.abko.todo.global.exception.ExceptionEnum;
import indiv.abko.todo.global.util.Encrypt;
import indiv.abko.todo.todo.comment.service.CommentService;
import indiv.abko.todo.todo.dto.TodoCreateReq;
import indiv.abko.todo.todo.dto.TodoSearchCondition;
import indiv.abko.todo.todo.dto.TodoUpdateReq;
import indiv.abko.todo.todo.dto.TodoWithCommentsResp;
import indiv.abko.todo.todo.entity.Todo;
import indiv.abko.todo.todo.mapper.TodoMapper;
import indiv.abko.todo.todo.repository.TodoRepository;
import indiv.abko.todo.todo.repository.TodoSortBuilder;
import indiv.abko.todo.todo.repository.TodoSpecBuilder;
import indiv.abko.todo.todo.dto.TodoListResp;
import indiv.abko.todo.todo.dto.TodoResp;
import lombok.RequiredArgsConstructor;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepo;
    private final TodoMapper todoMapper;
    private final CommentService commentService;
    private final Encrypt encrypt;

    /**
     * 주어진 요청 데이터로 새로운 Todo 항목을 생성한다.
     *
     * @param todoReq 새 Todo의 정보를 담은 요청 객체
     * @return 생성된 Todo 정보를 담은 {@link TodoResp}
     */
    @Transactional
    public TodoResp create(final TodoCreateReq todoReq) {
        final Todo todo = todoMapper.toTodo(todoReq);
        final var result = todoRepo.save(todo);
        return todoMapper.toTodoResp(result);
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
        final Todo todo = retrieveOrThrow(id);
        final TodoResp todoResp = todoMapper.toTodoResp(todo);
        final var commentResps = commentService.getComments(todo);
        return new TodoWithCommentsResp(todoResp, commentResps);
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
        final var spec = TodoSpecBuilder.builder()
            .authorLike(condition.author())
            .contentLike(condition.content())
            .titleLike(condition.title())
            .build();
        final var sort = TodoSortBuilder.buildWith(condition.orderBy());
        final var todos = todoRepo.findAll(spec, sort);
        return new TodoListResp(todos.stream()
            .map(todoMapper::toTodoResp)
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
        final var todo = retrieveOrThrow(id);
        todo.verifyPassword(updateReq.password(), encrypt);
        todo.updatePresented(updateReq.title(), updateReq.author());
        return todoMapper.toTodoResp(todo);
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
        final String decodedPassword = new String(Base64.getDecoder().decode(encodedPassword));
        final var todo = retrieveTodoWithAuth(id, decodedPassword);
        todoRepo.delete(todo);
    }

    private Todo retrieveTodoWithAuth(Long id, String password) {
        final var todo = retrieveOrThrow(id);
        hasAuthOrThrow(todo, password);
        return todo;
    }

    private void hasAuthOrThrow(final Todo todo, final String password) {
        final var auth = encrypt.isHashEqual(password, todo.getPassword());
        if (auth == false) {
            throw new BusinessException(ExceptionEnum.TODO_PERMISSION_DENIED);
        }
    }
}
