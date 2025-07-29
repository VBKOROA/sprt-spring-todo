package indiv.abko.todo.todo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import indiv.abko.todo.common.dto.ApiResponse;
import indiv.abko.todo.todo.dto.CreateTodoReq;
import indiv.abko.todo.todo.dto.CreateTodoResp;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<CreateTodoResp>> createTodo(@RequestBody CreateTodoReq todoReq) {
        var result = todoService.create(todoReq);

        ApiResponse<CreateTodoResp> apiResponse = new ApiResponse<>(
            HttpStatus.CREATED,
            "",
            result
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}
