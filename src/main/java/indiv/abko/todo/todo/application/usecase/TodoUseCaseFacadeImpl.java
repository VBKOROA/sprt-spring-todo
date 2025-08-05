package indiv.abko.todo.todo.application.usecase;

import indiv.abko.todo.todo.application.port.in.TodoUseCaseFacade;
import indiv.abko.todo.todo.application.port.in.command.*;
import indiv.abko.todo.todo.domain.Comment;
import indiv.abko.todo.todo.domain.Todo;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoUseCaseFacadeImpl implements TodoUseCaseFacade {
    private final AddCommentUseCase addCommentUseCase;
    private final DeleteTodoUseCase deleteTodoUseCase;
    private final CreateTodoUseCase createTodoUseCase;
    private final UpdateTodoUseCase updateTodoUseCase;
    private final SearchTodosUseCase searchTodosUseCase;
    private final GetTodoUseCase getTodoUseCase;

    @Override
    public Comment addComment(AddCommentCommand command) {
        return addCommentUseCase.execute(command);
    }

    @Override
    public void deleteTodo(DeleteTodoCommand command) {
        deleteTodoUseCase.execute(command);
    }

    @Override
    public Todo createTodo(CreateTodoCommand command) {
        return createTodoUseCase.execute(command);
    }

    @Override
    public Todo updateTodo(UpdateTodoCommand command) { return updateTodoUseCase.execute(command); }

    @Override
    public List<Todo> searchTodos(SearchTodosCommand command) { return searchTodosUseCase.execute(command); }

    @Override
    public Todo getTodo(GetTodoCommand command) { return getTodoUseCase.execute(command); }
}
