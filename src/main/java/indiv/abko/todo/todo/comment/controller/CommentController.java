package indiv.abko.todo.todo.comment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import indiv.abko.todo.todo.comment.service.CommentService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/v1/todos/{todoId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
}
