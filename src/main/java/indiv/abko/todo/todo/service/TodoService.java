package indiv.abko.todo.todo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import indiv.abko.todo.global.exception.BusinessException;
import indiv.abko.todo.global.exception.ExceptionEnum;
import indiv.abko.todo.global.util.Encrypt;
import indiv.abko.todo.todo.comment.dto.CommentResp;
import indiv.abko.todo.todo.comment.mapper.CommentMapper;
import indiv.abko.todo.todo.dto.TodoCreateReq;
import indiv.abko.todo.todo.dto.TodoSearchCondition;
import indiv.abko.todo.todo.dto.TodoUpdateReq;
import indiv.abko.todo.todo.dto.TodoWithCommentsResp;
import indiv.abko.todo.todo.entity.Todo;
import indiv.abko.todo.todo.mapper.TodoMapper;
import indiv.abko.todo.todo.repository.TodoRepository;
import indiv.abko.todo.todo.dto.TodoListResp;
import indiv.abko.todo.todo.dto.TodoResp;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepo;
    private final TodoMapper todoMapper;
    private final CommentMapper commentMapper;
    private final Encrypt encrypt;

    /**
     * 주어진 요청 데이터로 새로운 Todo 항목을 생성한다.
     * 
     * {@link TodoCreateReq}로부터 {@link Todo} 엔티티를 만들고,
     * 비밀번호를 암호화한 뒤, 저장소에 저장한다.
     * 생성된 Todo의 정보를 담은 응답 DTO를 반환한다.
     * 
     *
     * @param todoReq 새 Todo의 정보를 담은 요청 객체
     * @return 생성된 Todo 정보를 담은 {@link TodoResp}
     */
    @Transactional
    public TodoResp create(TodoCreateReq todoReq) {
        Todo todo = todoMapper.toTodo(todoReq);
        var result = todoRepo.save(todo);
        var response = todoMapper.toTodoResp(result);

        return response;
    }

    /**
     * 주어진 ID에 해당하는 Todo와 그에 연결된 댓글 목록을 함께 조회한다.
     *
     * @param id 조회할 {@link Todo}의 ID
     * @return Todo 정보와 댓글 목록을 포함하는 {@link TodoWithCommentsResp} 객체
     * @throws BusinessException 해당 ID의 Todo가 존재하지 않을 경우 발생
     */
    public TodoWithCommentsResp getTodoWithComments(Long id) {
        Todo todo = findOrThrow(id);
        TodoResp todoResp = todoMapper.toTodoResp(todo);
        var commentResps = todo.getComments().stream().map(commentMapper::toCommentResp).toList();
        return new TodoWithCommentsResp(todoResp, commentResps);
    }

    private Todo findOrThrow(Long id) {
        return todoRepo.findById(id)
                .orElseThrow(() -> new BusinessException(ExceptionEnum.TODO_NOT_FOUND));
    }

    /**
     * 주어진 조건에 따라 Todo 목록을 조회한다.
     *
     * @param condition Todo 조회 조건 객체
     * @return 조건에 맞는 {@Link TodoListResp} 객체
     */
    @Transactional(readOnly = true)
    public TodoListResp fetchFilteredTodos(TodoSearchCondition condition) {
        var todos = todoRepo.fetchFilteredTodos(condition);
        return new TodoListResp(
                todos.stream().map(todoMapper::toTodoResp).toList());
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
    public TodoResp updateTodo(Long id, TodoUpdateReq updateReq) {
        var todo = findOrThrow(id);
        hasAuthOrThrow(todo, updateReq.password());
        todo.update(updateReq.title(), updateReq.author());
        return todoMapper.toTodoResp(todo);
    }

    /**
     * 주어진 ID와 비밀번호를 사용하여 Todo를 삭제한다.
     * 
     * @param id       삭제할 {@link Todo}의 고유 ID
     * @param password Todo 삭제를 위한 인증 비밀번호
     * @throws BusinessException - 주어진 ID에 해당하는 Todo가 존재하지 않을 경우
     *                           - 비밀번호가 일치하지 않을 경우
     */
    @Transactional
    public void deleteTodo(Long id, String password) {
        var todo = findOrThrow(id);
        hasAuthOrThrow(todo, password);
        todoRepo.delete(todo);
    }

    private void hasAuthOrThrow(Todo todo, String password) {
        var auth = encrypt.isHashEqual(password, todo.getPassword());
        if (auth == false) {
            throw new BusinessException(ExceptionEnum.TODO_PERMISSION_DENIED);
        }
    }
}
