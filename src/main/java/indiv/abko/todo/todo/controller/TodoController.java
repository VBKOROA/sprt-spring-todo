package indiv.abko.todo.todo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import indiv.abko.todo.global.dto.ApiResp;
import indiv.abko.todo.global.validation.ShouldBase64;
import indiv.abko.todo.todo.dto.TodoCreateReq;
import indiv.abko.todo.todo.dto.TodoSearchCondition;
import indiv.abko.todo.todo.dto.TodoUpdateReq;
import indiv.abko.todo.todo.dto.TodoWithCommentsResp;
import indiv.abko.todo.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "Todo API", description = "할일 관리 시스템의 Todo 관련 API")
public class TodoController {
    private final TodoService todoService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "새 Todo 생성", description = "새 Todo 생성")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Todo가 성공적으로 생성됨"),
        @ApiResponse(responseCode = "400", description = "파라미터가 유효하지 않음",
            content = @Content(
                schema = @Schema(implementation = ApiResp.class),
                examples = @ExampleObject(value = "{\"status\":\"BAD_REQUEST\",\"message\":\"요청이 잘못되었습니다.\",\"data\":null}")
            ))
    })
    public ApiResp<TodoResp> createTodo(@RequestBody @Valid TodoCreateReq createReq) {
        var todoResponse = todoService.create(createReq);

        return ApiResp.created(todoResponse);
    }

    // @ModelAttribute: 여러 개의 파라미터를 객체로 바인딩 할 수 있음
    @GetMapping("")
    @Operation(summary = "Todo 목록 조회", description = "Todo 목록을 조회함. 필터링 및 정렬 기능을 지원함.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Todo 목록이 성공적으로 조회됨"),
        @ApiResponse(responseCode = "400", description = "파라미터가 유효하지 않음",
            content = @Content(
                schema = @Schema(implementation = ApiResp.class),
                examples = @ExampleObject(value = "{\"status\":\"BAD_REQUEST\",\"message\":\"요청이 잘못되었습니다.\",\"data\":null}")
            ))
    })
    public ApiResp<TodoListResp> getTodos(
            @ModelAttribute @Parameter(name = "condition", description = "검색 조건") TodoSearchCondition condition) {
        return ApiResp.ok(todoService.fetchFilteredTodos(condition));
    }

    @GetMapping("/{id}")
    public ApiResp<TodoWithCommentsResp> getTodo(@PathVariable("id") Long id) {
        return ApiResp.ok(todoService.getTodoWithComments(id));
    }

    @PatchMapping("/{id}")
    public ApiResp<TodoResp> updateTodo(@PathVariable("id") Long id, @RequestBody @Valid TodoUpdateReq updateReq) {
        return ApiResp.ok(todoService.updateTodo(id, updateReq));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodo(@PathVariable("id") Long id,
        @RequestHeader("X-Todo-Password") @ShouldBase64 String password) {
        String decodedPassword = new String(Base64.getDecoder().decode(password));
        todoService.deleteTodo(id, decodedPassword);
    }
}
