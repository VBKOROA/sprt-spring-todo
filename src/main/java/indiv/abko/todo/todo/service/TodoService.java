package indiv.abko.todo.todo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import indiv.abko.todo.global.exception.BusinessException;
import indiv.abko.todo.global.exception.ExceptionEnum;
import indiv.abko.todo.todo.dto.TodoCreateReq;
import indiv.abko.todo.todo.dto.TodoSearchCondition;
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
        var todo = todoRepo.findById(id)
                .orElseThrow(() -> new BusinessException(ExceptionEnum.TODO_NOT_FOUND));
        return todoMapper.toTodoResp(todo);
    }

    /**
     * 주어진 조건에 따라 할 일 목록을 조회한다.
     *
     * @param condition 할 일 조회 조건 객체
     * @return 조건에 맞는 {@Link TodoListResp} 객체
     */
    @Transactional(readOnly = true)
    public TodoListResp fetchFilteredTodos(TodoSearchCondition condition) {
        var todos = todoRepo.fetchFilteredTodos(condition);
        return new TodoListResp(
                todos.stream().map(todoMapper::toTodoResp).toList());
    }
}
