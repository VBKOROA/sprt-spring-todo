package indiv.abko.todo.todo;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import indiv.abko.todo.common.exception.BusinessException;
import indiv.abko.todo.common.exception.ExceptionEnum;
import indiv.abko.todo.todo.dto.CreateTodoReq;
import indiv.abko.todo.todo.dto.CreateTodoResp;
import indiv.abko.todo.todo.dto.GetTodoResp;
import indiv.abko.todo.todo.dto.GetTodosResp;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepo;
    private final TodoMapper todoMapper;

    /**
     * 주어진 요청 데이터로 새로운 Todo 항목을 생성한다.
     * 
     * {@link CreateTodoReq}로부터 {@link Todo} 엔티티를 만들고,
     * 비밀번호를 암호화한 뒤, 저장소에 저장한다.
     * 생성된 Todo의 정보를 담은 응답 DTO를 반환한다.
     * 
     *
     * @param todoReq 새 Todo의 정보를 담은 요청 객체
     * @return 생성된 Todo 정보를 담은 {@link CreateTodoResp}
     */
    @Transactional
    public CreateTodoResp create(CreateTodoReq todoReq) {
        Todo todo = todoMapper.toTodo(todoReq);
        var result = todoRepo.save(todo);
        var response = todoMapper.toCreateTodoResp(result);

        return response;
    } 

    /**
     * 지정한 작성자의 todo 목록을 수정일 기준 내림차순으로 가져온다.
     *
     * @param author todo를 조회할 작성자 이름 또는 아이디
     * @return 해당 작성자의 todo 목록을 담은 {@link GetTodosResp} 객체 (최신 수정순)
     */
    @Transactional(readOnly = true)
    public GetTodosResp getTodosByAuthorOrderByModifiedAtDesc(String author) {
        var todos = todoRepo.findByAuthorOrderByModifiedAtDesc(author);
        return mapTodosToResponse(todos);
    }

    /**
     * 모든 Todo 항목을 수정일 기준 내림차순으로 조회한다.
     *
     * @return 수정일 기준(최신순)으로 정렬된 Todo 목록을 담은 {@link GetTodosResp} 객체
     */
    @Transactional(readOnly = true)
    public GetTodosResp getTodosOrderByModifiedAtDesc() {
        var todos = todoRepo.findByOrderByModifiedAtDesc();
        return mapTodosToResponse(todos);
    }

    private GetTodosResp mapTodosToResponse(List<Todo> todos) {
        var todoDtos = todos.stream().map(todoMapper::toTodoDto).toList();
        
        return new GetTodosResp(todoDtos);
    }

    /**
     * 주어진 ID에 해당하는 Todo를 조회한다.
     *
     * @param id 조회할 Todo의 ID
     * @return 조회된 Todo 정보를 담은 GetTodoResp 객체
     * @throws BusinessException Todo를 찾을 수 없는 경우 발생
     */
    public GetTodoResp getTodo(Long id) {
        var todo = todoRepo.findById(id)
            .orElseThrow(() -> new BusinessException(ExceptionEnum.TODO_NOT_FOUND));
        return todoMapper.toGetTodoResp(todo);
    }
}
