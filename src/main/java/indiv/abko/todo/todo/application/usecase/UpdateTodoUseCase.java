package indiv.abko.todo.todo.application.usecase;

import indiv.abko.todo.todo.domain.port.out.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import indiv.abko.todo.global.exception.BusinessException;
import indiv.abko.todo.todo.application.port.in.command.UpdateTodoCommand;
import indiv.abko.todo.todo.domain.port.out.TodoRepository;
import indiv.abko.todo.todo.domain.Todo;
import indiv.abko.todo.todo.domain.exception.TodoExceptionEnum;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateTodoUseCase {
    private final TodoRepository todoRepo;
    private final PasswordEncoder passwordEncoder;

    /**
     * 주어진 ID에 해당하는 Todo 항목을 업데이트한다.
     *
     * @param updateCommand 업데이트할 Todo의 ID, 제목, 작성자, 비밀번호를 포함한 명령 객체
     *                      - {@code id}: 업데이트할 Todo의 고유 식별자
     *                      - {@code title}: 변경할 제목
     *                      - {@code author}: 변경할 작성자
     *                      - {@code password}: 인증에 사용할 비밀번호(평문)
     * @return 업데이트된 Todo 엔티티
     * @throws BusinessException Todo 항목을 찾을 수 없거나 비밀번호가 일치하지 않을 경우
     */
    @Transactional
    public Todo execute(UpdateTodoCommand updateCommand) {
        final Todo todo = getAggregateOrThrow(updateCommand.id());
        todo.shouldHaveAuth(updateCommand.password(), passwordEncoder);
        todo.updatePresented(updateCommand.title(), updateCommand.author());
        return todoRepo.save(todo);
    }

    private Todo getAggregateOrThrow(final Long id) {
        return todoRepo.findAggregate(id)
                .orElseThrow(() -> new BusinessException(TodoExceptionEnum.TODO_NOT_FOUND));
    }
}
