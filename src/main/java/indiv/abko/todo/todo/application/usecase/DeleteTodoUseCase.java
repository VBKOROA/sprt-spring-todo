package indiv.abko.todo.todo.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import indiv.abko.todo.global.exception.BusinessException;
import indiv.abko.todo.todo.application.port.in.command.DeleteTodoCommand;
import indiv.abko.todo.todo.domain.port.out.PasswordDecoder;
import indiv.abko.todo.todo.domain.port.out.PasswordEncoder;
import indiv.abko.todo.todo.domain.port.out.TodoRepository;
import indiv.abko.todo.todo.domain.Todo;
import indiv.abko.todo.todo.domain.exception.TodoExceptionEnum;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteTodoUseCase {
    private final PasswordDecoder passwordDecoder;
    private final TodoRepository todoRepo;
    private final PasswordEncoder passwordEncoder;

    /**
     * 주어진 ID와 인코딩된 비밀번호를 사용하여 Todo를 삭제한다.
     *
     * @param command 삭제할 Todo의 ID와 인코딩된 비밀번호를 포함한 명령 객체
     * @return 항상 {@code null}
     * @throws BusinessException 주어진 ID에 해당하는 Todo가 존재하지 않을 경우 또는 비밀번호가 일치하지 않을 경우 발생
     */
    @Transactional
    public Void execute(DeleteTodoCommand command) {
        final String decodedPassword = passwordDecoder.decode(command.encodedPassword());
        final Todo todo = todoRepo.findSummary(command.id())
            .orElseThrow(() -> new BusinessException(TodoExceptionEnum.TODO_NOT_FOUND));
        todo.shouldHaveAuth(decodedPassword, passwordEncoder);
        todoRepo.delete(todo);
        return null;
    }
}
