package indiv.abko.todo.todo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import indiv.abko.todo.global.exception.BusinessException;
import indiv.abko.todo.global.exception.ExceptionEnum;
import indiv.abko.todo.global.util.Encrypt;
import indiv.abko.todo.todo.dto.TodoCreateReq;
import indiv.abko.todo.todo.dto.TodoSearchCondition;
import indiv.abko.todo.todo.dto.TodoUpdateReq;
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
     * 주어진 ID에 해당하는 Todo를 조회한다.
     *
     * @param id 조회할 Todo의 ID
     * @return 조회된 Todo 정보를 담은 {@Link TodoResp} 객체
     * @throws BusinessException Todo를 찾을 수 없는 경우 발생
     */
    public TodoResp getTodo(Long id) {
        var todo = findOrThrow(id);
        return todoMapper.toTodoResp(todo);
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
     * @param id 업데이트할 {@link Todo} 항목의 ID
     * @param updateReq 업데이트 요청 데이터
     * @return 업데이트된 Todo 항목의 응답 객체
     * @throws BusinessException 비밀번호가 일치하지 않아 권한이 없을 경우 발생
     */
    @Transactional
    public TodoResp updateTodo(Long id, TodoUpdateReq updateReq) {
        var todo = findOrThrow(id);
        var hasAuth = encrypt.isHashEqual(updateReq.password(), todo.getPassword());
        if(hasAuth == false) {
            throw new BusinessException(ExceptionEnum.TODO_UPDATE_UNAUTHORIZED);
        }
        if(updateReq.title() != null) {
            todo.updateTitle(updateReq.title());
        }
        if(updateReq.author() != null) {
            todo.updateAuthor(updateReq.author());
        }
        return todoMapper.toTodoResp(todo);
    }
}
