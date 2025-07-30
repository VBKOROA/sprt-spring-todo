package indiv.abko.todo.todo.comment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import indiv.abko.todo.global.util.Encrypt;
import indiv.abko.todo.todo.comment.dto.CommentResp;
import indiv.abko.todo.todo.comment.dto.CommentWriteReq;
import indiv.abko.todo.todo.comment.entity.Comment;

@Mapper(componentModel = "spring", uses = Encrypt.class)
public interface CommentMapper {
    CommentResp toCommentResp(Comment comment);

    @Mapping(target = "password", qualifiedByName = "hashPassword")
    Comment toComment(CommentWriteReq dto);
}
