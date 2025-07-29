package indiv.abko.todo.todo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import indiv.abko.todo.common.util.Encrypt;
import indiv.abko.todo.todo.dto.CreateTodoReq;
import indiv.abko.todo.todo.dto.CreateTodoResp;
import indiv.abko.todo.todo.dto.GetTodosResp;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepo;
    private final Encrypt encrypt;

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
        Todo todo = Todo.builder()
            .title(todoReq.title())
            .content(todoReq.content())
            .author(todoReq.author())
            .password(encrypt.hash(todoReq.password()))
            .build();
        
        var result = todoRepo.save(todo);

        var response = new CreateTodoResp(
            result.getTitle(),
            result.getContent(),
            result.getAuthor(),
            result.getCreatedAt(),
            result.getModifiedAt()
        );

        return response;
    } 

    @Transactional(readOnly = true)
    public GetTodosResp getTodosByAuthorOrderByModifiedAtDesc(String author) {
        var todos = todoRepo.findByAuthorContainingOrderByModifiedAtDesc(author);

        var todoDtos = todos.stream().map(todo -> {
            return new GetTodosResp.TodoDto(
                todo.getTitle(),
                todo.getContent(),
                todo.getAuthor(),
                todo.getCreatedAt(),
                todo.getModifiedAt()
            );
        }).toList();
        
        return new GetTodosResp(todoDtos);
    }
}
