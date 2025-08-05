package indiv.abko.todo.todo.adapter.in.rest;

import indiv.abko.todo.todo.adapter.in.rest.mapper.CommentMapper;
import indiv.abko.todo.todo.adapter.in.rest.mapper.TodoMapper;
import indiv.abko.todo.todo.application.port.in.TodoUseCaseFacade;
import indiv.abko.todo.todo.application.port.in.command.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import indiv.abko.todo.global.dto.ApiResp;
import indiv.abko.todo.todo.adapter.in.rest.dto.todo.TodoCreateReq;
import indiv.abko.todo.todo.adapter.in.rest.dto.todo.TodoListResp;
import indiv.abko.todo.todo.adapter.in.rest.dto.todo.TodoResp;
import indiv.abko.todo.todo.adapter.in.rest.dto.todo.TodoSearchCondition;
import indiv.abko.todo.todo.adapter.in.rest.dto.todo.TodoUpdateReq;
import indiv.abko.todo.todo.adapter.in.rest.dto.todo.TodoWithCommentsResp;
import indiv.abko.todo.todo.adapter.in.rest.validation.ShouldBase64;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
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
    private final TodoUseCaseFacade todoUseCaseFacade;
    private final TodoMapper todoMapper;
    private final CommentMapper commentMapper;

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
        var command = CreateTodoCommand.builder()
                .title(createReq.title())
                .author(createReq.author())
                .password(createReq.password())
                .content(createReq.content())
                .build();
        var createdTodo = todoUseCaseFacade.createTodo(command);
        return ApiResp.created(todoMapper.toTodoResp(createdTodo));
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
            @ModelAttribute 
            @ParameterObject
            TodoSearchCondition condition) {
        var command = SearchTodosCommand.builder()
                .title(condition.title())
                .author(condition.author())
                .content(condition.content())
                .orderBy(condition.orderBy())
                .build();
        var todos = todoUseCaseFacade.searchTodos(command);
        var responseTodos = todoMapper.toTodoResps(todos);
        var responseData = new TodoListResp(responseTodos);
        return ApiResp.ok(responseData);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Todo 조회", description = "Todo를 조회함. 해당 Todo와 연결된 댓글 목록도 함께 조회함.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Todo가 성공적으로 조회됨"),
        @ApiResponse(responseCode = "404", description = "Todo를 찾을 수 없음",
            content = @Content(
                schema = @Schema(implementation = ApiResp.class),
                examples = @ExampleObject(value = "{\"status\":\"NOT_FOUND\",\"message\":\"Todo를 찾을 수 없습니다.\",\"data\":null}")
            ))
    })
    public ApiResp<TodoWithCommentsResp> getTodo(
        @PathVariable("id") 
        @Parameter(name = "id", description = "Todo ID") 
        long id) {
        var todo = todoUseCaseFacade.getTodo(new GetTodoCommand(id));
        var responseTodo = todoMapper.toTodoResp(todo);
        var responseComments = commentMapper.toCommentResps(todo.getComments());
        var responseData = new TodoWithCommentsResp(responseTodo, responseComments);
        return ApiResp.ok(responseData);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Todo 수정", description = "Todo를 수정함. 수정할 수 있는 필드는 제목, 작성자임.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Todo가 성공적으로 수정됨"),
        @ApiResponse(responseCode = "400", description = "파라미터가 유효하지 않음",
            content = @Content(
                schema = @Schema(implementation = ApiResp.class),
                examples = @ExampleObject(value = "{\"status\":\"BAD_REQUEST\",\"message\":\"요청이 잘못되었습니다.\",\"data\":null}")
            )),
        @ApiResponse(responseCode = "401", description = "인증 실패",
            content = @Content(
                schema = @Schema(implementation = ApiResp.class),
                examples = @ExampleObject(value = "{\"status\":\"UNAUTHORIZED\",\"message\":\"인증에 실패하였습니다.\",\"data\":null}")
            )),
        @ApiResponse(responseCode = "404", description = "Todo를 찾을 수 없음",
            content = @Content(
                schema = @Schema(implementation = ApiResp.class),
                examples = @ExampleObject(value = "{\"status\":\"NOT_FOUND\",\"message\":\"Todo를 찾을 수 없습니다.\",\"data\":null}")
            ))
    })
    public ApiResp<TodoResp> updateTodo(
        @PathVariable("id") 
        @Parameter(name = "id", description = "Todo ID") 
        long id,
        @RequestBody 
        @Valid 
        TodoUpdateReq updateReq) {
        var command =  UpdateTodoCommand.builder()
                .id(id)
                .title(updateReq.title())
                .author(updateReq.author())
                .password(updateReq.password())
                .build();
        var updatedTodo = todoUseCaseFacade.updateTodo(command);
        return ApiResp.ok(todoMapper.toTodoResp(updatedTodo));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Todo 삭제", description = "Todo를 삭제함.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Todo가 성공적으로 삭제됨"),
        @ApiResponse(responseCode = "401", description = "인증 실패",
            content = @Content(
                schema = @Schema(implementation = ApiResp.class),
                examples = @ExampleObject(value = "{\"status\":\"UNAUTHORIZED\",\"message\":\"인증에 실패하였습니다.\",\"data\":null}")
            )),
        @ApiResponse(responseCode = "404", description = "Todo를 찾을 수 없음",
            content = @Content(
                schema = @Schema(implementation = ApiResp.class),
                examples = @ExampleObject(value = "{\"status\":\"NOT_FOUND\",\"message\":\"Todo를 찾을 수 없습니다.\",\"data\":null}")
            ))
    })
    public void deleteTodo(
        @Parameter(name = "id", description = "Todo ID")
        @PathVariable("id") 
        Long id,
        @Parameter(name = "X-Todo-Password", 
            in = ParameterIn.HEADER, 
            description = "Todo 삭제를 위한 인증 비밀번호 / base64로 인코딩되어야 함.", 
            required = true)
        @RequestHeader("X-Todo-Password") 
        @ShouldBase64 
        String encodedPassword) {
        var command =   DeleteTodoCommand.builder()
                .id(id)
                .encodedPassword(encodedPassword)
                .build();
        todoUseCaseFacade.deleteTodo(command);
    }
}
