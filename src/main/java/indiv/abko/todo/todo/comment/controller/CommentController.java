package indiv.abko.todo.todo.comment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import indiv.abko.todo.global.dto.ApiResponse;
import indiv.abko.todo.todo.comment.dto.CommentResp;
import indiv.abko.todo.todo.comment.dto.CommentWriteReq;
import indiv.abko.todo.todo.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/api/v1/todos/{todoId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("")
    public ApiResponse<CommentResp> writeComment(@PathVariable("toodId") Long todoId, @RequestBody CommentWriteReq req) {
        return ApiResponse.ok(commentService.createComment(todoId, req));
    }
}
