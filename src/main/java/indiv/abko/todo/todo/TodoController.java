package indiv.abko.todo.todo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import indiv.abko.todo.common.dto.ApiResponse;
import indiv.abko.todo.todo.dto.CreateTodoReq;
import indiv.abko.todo.todo.dto.CreateTodoResp;
import indiv.abko.todo.todo.dto.GetTodoResp;
import indiv.abko.todo.todo.dto.GetTodosCondition;
import indiv.abko.todo.todo.dto.GetTodosResp;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateTodoResp> createTodo(@RequestBody CreateTodoReq todoReq) {
        var todo = todoService.create(todoReq);

        return ApiResponse.created(todo);
    }

    // @ModelAttribute: 여러 개의 파라미터를 객체로 바인딩 할 수 있음
    @GetMapping("")
    public ApiResponse<GetTodosResp> getTodosWithCondition(
            @ModelAttribute GetTodosCondition condition) {
        GetTodosResp todos;

        if (condition.isNull()) {
            todos = todoService.getTodosOrderByModifiedAtDesc();
        } else if (condition.orderBy().equals("modifiedAtDesc")) {
            todos = todoService.getTodosByAuthorOrderByModifiedAtDesc(condition.author());
        } else {
            todos = todoService.getTodosOrderByModifiedAtDesc();
        }

        return ApiResponse.ok(todos);
    }

    @GetMapping("/{id}")
    public ApiResponse<GetTodoResp> getTodo(@PathVariable("id") Long id) {
        return ApiResponse.ok(todoService.getTodo(id));
    }
}
