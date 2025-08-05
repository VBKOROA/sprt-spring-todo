package indiv.abko.todo.todo.application.usecase;

import indiv.abko.todo.todo.domain.port.out.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import indiv.abko.todo.global.exception.BusinessException;
import indiv.abko.todo.todo.application.port.in.command.AddCommentCommand;
import indiv.abko.todo.todo.domain.port.out.TodoRepository;
import indiv.abko.todo.todo.domain.Comment;
import indiv.abko.todo.todo.domain.Todo;
import indiv.abko.todo.todo.domain.exception.TodoExceptionEnum;
import indiv.abko.todo.todo.domain.vo.ContentVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddCommentUseCase {
    private final TodoRepository todoRepo;
    private final PasswordEncoder passwordEncoder;

    /**
     * 지정된 Todo 항목에 새로운 댓글을 추가한다.
     *
     * @param command 댓글이 추가될 Todo의 ID, 내용, 작성자, 비밀번호를 포함한 명령 객체
     *                - {@code todoId}: 댓글이 추가될 Todo의 고유 식별자
     *                - {@code content}: 댓글 내용
     *                - {@code author}: 댓글 작성자
     *                - {@code password}: 댓글 비밀번호
     * @return 생성된 댓글 엔티티
     * @throws BusinessException Todo 항목을 찾을 수 없거나 댓글 제한을 초과한 경우 {@code TODO_NOT_FOUND} 또는 기타 예외 발생
     */
    @Transactional
    public Comment execute(AddCommentCommand command) {
        final Todo todo = todoRepo.findAggregate(command.todoId())
            .orElseThrow(() -> new BusinessException(TodoExceptionEnum.TODO_NOT_FOUND));
        final var encodedPassword = passwordEncoder.encode(command.password());
        final Comment comment = Comment.builder()
            .content(new ContentVO(command.content()))
            .author(command.author())
            .password(encodedPassword)
            .build();
        todo.addComment(comment);
        final Todo savedTodo = todoRepo.saveComment(todo);
        return savedTodo.getLastComment();
    }
}
