package indiv.abko.todo.todo.adapter.in.rest;

import indiv.abko.todo.todo.adapter.in.rest.dto.comment.CommentResp;
import indiv.abko.todo.todo.adapter.in.rest.dto.comment.CommentWriteReq;
import indiv.abko.todo.todo.adapter.in.rest.mapper.CommentMapper;
import indiv.abko.todo.todo.application.port.in.TodoUseCaseFacade;
import indiv.abko.todo.todo.application.port.in.command.AddCommentCommand;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import indiv.abko.todo.global.dto.ApiResp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/todos/{todoId}/comments")
@RequiredArgsConstructor
@Tag(name = "Comment API", description = "할일 관리 시스템의 댓글 관련 API")
public class CommentController {
    private final TodoUseCaseFacade todoUseCaseFacade;
    private final CommentMapper commentMapper;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "댓글 작성", description = "특정 할일에 대한 댓글을 작성함.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "댓글이 성공적으로 작성됨"),
        @ApiResponse(responseCode = "400", description = "파라미터가 유효하지 않음",
            content = @Content(
                schema = @Schema(implementation = ApiResp.class),
                examples = @ExampleObject(value = "{\"status\":\"BAD_REQUEST\",\"message\":\"요청이 잘못되었습니다.\",\"data\":null}")
            ))
    })
    public ApiResp<CommentResp> writeComment(
        @PathVariable("todoId")
        @Parameter(name = "todoId", description = "할일 ID")
        long todoId,
        @RequestBody
        @Valid
        CommentWriteReq req) {
        var command = AddCommentCommand.builder()
                .todoId(todoId)
                .author(req.author())
                .content(req.content())
                .password(req.password())
                .build();
        var addedComment = todoUseCaseFacade.addComment(command);
        return ApiResp.created(commentMapper.toCommentResp(addedComment));
    }
}
