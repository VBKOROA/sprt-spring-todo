package indiv.abko.todo.todo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import indiv.abko.todo.global.dto.ApiResponse;
import indiv.abko.todo.global.validation.ShouldBase64;
import indiv.abko.todo.todo.dto.TodoCreateReq;
import indiv.abko.todo.todo.dto.TodoSearchCondition;
import indiv.abko.todo.todo.dto.TodoUpdateReq;
import indiv.abko.todo.todo.dto.TodoWithCommentsResp;
import indiv.abko.todo.todo.service.TodoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import indiv.abko.todo.todo.dto.TodoListResp;
import indiv.abko.todo.todo.dto.TodoResp;

import lombok.RequiredArgsConstructor;
import java.util.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
@Validated
public class TodoController {
    private final TodoService todoService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<TodoResp> createTodo(@RequestBody @Valid TodoCreateReq createReq) {
        var todoResponse = todoService.create(createReq);

        return ApiResponse.created(todoResponse);
    }

    // @ModelAttribute: 여러 개의 파라미터를 객체로 바인딩 할 수 있음
    @GetMapping("")
    public ApiResponse<TodoListResp> getTodos(
            @ModelAttribute TodoSearchCondition condition) {
        return ApiResponse.ok(todoService.fetchFilteredTodos(condition));
    }

    @GetMapping("/{id}")
    public ApiResponse<TodoWithCommentsResp> getTodo(@PathVariable("id") Long id) {
        return ApiResponse.ok(todoService.getTodoWithComments(id));
    }

    @PatchMapping("/{id}")
    public ApiResponse<TodoResp> updateTodo(@PathVariable("id") Long id, @RequestBody @Valid TodoUpdateReq updateReq) {
        return ApiResponse.ok(todoService.updateTodo(id, updateReq));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodo(@PathVariable("id") Long id,
        @RequestHeader("X-Todo-Password") @ShouldBase64 String password) {
        String decodedPassword = new String(Base64.getDecoder().decode(password));
        todoService.deleteTodo(id, decodedPassword);
    }
}
