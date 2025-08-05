package indiv.abko.todo.todo.application.port.in;

import indiv.abko.todo.todo.application.port.in.command.*;
import indiv.abko.todo.todo.domain.Comment;
import indiv.abko.todo.todo.domain.Todo;

import java.util.List;

public interface TodoUseCaseFacade {
    public Comment addComment(AddCommentCommand command);

    public void deleteTodo(DeleteTodoCommand command);

    public Todo createTodo(CreateTodoCommand command);

    public Todo updateTodo(UpdateTodoCommand command);

    public List<Todo> searchTodos(SearchTodosCommand command);

    public Todo getTodo(GetTodoCommand command);
}
