package indiv.abko.todo.todo;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import indiv.abko.todo.todo.dto.CreateTodoReq;
import indiv.abko.todo.todo.dto.CreateTodoResp;
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

    @Transactional(readOnly = true)
    public GetTodosResp getTodosByAuthorOrderByModifiedAtDesc(String author) {
        var todos = todoRepo.findByAuthorOrderByModifiedAtDesc(author);
        return mapTodosToResponse(todos);
    }

    @Transactional(readOnly = true)
    public GetTodosResp getTodosOrderByModifiedAtDesc() {
        var todos = todoRepo.findByOrderByModifiedAtDesc();
        return mapTodosToResponse(todos);
    }

    private GetTodosResp mapTodosToResponse(List<Todo> todos) {
        var todoDtos = todos.stream().map(todoMapper::toTodoDto).toList();
        
        return new GetTodosResp(todoDtos);
    } 
}
