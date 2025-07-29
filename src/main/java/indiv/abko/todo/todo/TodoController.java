package indiv.abko.todo.todo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import indiv.abko.todo.common.dto.ApiResponse;
import indiv.abko.todo.todo.dto.CreateTodoReq;
import indiv.abko.todo.todo.dto.CreateTodoResp;
import indiv.abko.todo.todo.dto.GetTodosCondition;
import indiv.abko.todo.todo.dto.GetTodosResp;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

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

    // @ModelAttribute: 여러 개의 파라미터를 객체로 바인딩 할 수 있음
    @GetMapping("")
    public ResponseEntity<ApiResponse<GetTodosResp>> getTodosWithCondition(@ModelAttribute GetTodosCondition condition) {
        GetTodosResp responseData;
        
        if(condition.isNull()) {
            responseData = todoService.getTodosOrderByModifiedAtDesc();
        } else if(condition.orderBy().equals("modifiedAtDesc")) {
            responseData = todoService.getTodosByAuthorOrderByModifiedAtDesc(condition.author());
        } else {
            responseData = todoService.getTodosOrderByModifiedAtDesc();
        }

        ApiResponse<GetTodosResp> response = new ApiResponse<>(
            HttpStatus.OK,
            "",
            responseData
        );

        return ResponseEntity.ok(response);
    }
}
