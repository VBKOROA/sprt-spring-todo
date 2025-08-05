package indiv.abko.todo.todo.adapter.in.rest.mapper;

import indiv.abko.todo.todo.adapter.in.rest.dto.comment.CommentResp;
import indiv.abko.todo.todo.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    public List<CommentResp> toCommentResps(final List<Comment> comments) {
        return comments.stream()
                .map(this::toCommentResp)
                .toList();
    }

    public CommentResp toCommentResp(final Comment comment) {
        return CommentResp.builder()
                .id(comment.getId())
                .author(comment.getAuthor())
                .content(comment.getContent().getContent())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }
}
