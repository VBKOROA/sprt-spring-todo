package indiv.abko.todo.todo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import indiv.abko.todo.common.util.Encrypt;
import indiv.abko.todo.todo.dto.CreateTodoReq;
import indiv.abko.todo.todo.dto.CreateTodoResp;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepo;
    private final Encrypt encrypt;

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
}
