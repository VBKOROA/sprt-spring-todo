package indiv.abko.todo.todo.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import indiv.abko.todo.global.exception.BusinessException;
import indiv.abko.todo.todo.application.port.in.command.GetTodoCommand;
import indiv.abko.todo.todo.domain.port.out.TodoRepository;
import indiv.abko.todo.todo.domain.Todo;
import indiv.abko.todo.todo.domain.exception.TodoExceptionEnum;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetTodoUseCase {
    private final TodoRepository todoRepo;

    /**
     * 주어진 ID를 사용하여 Todo 항목을 조회한다.
     *
     * @param getCommand 조회할 Todo의 ID를 포함한 명령 객체
     *                   - {@code id}: 조회할 Todo의 고유 식별자
     * @return 조회된 Todo 엔티티
     * @throws BusinessException Todo 항목을 찾을 수 없는 경우 {@code TODO_NOT_FOUND} 예외를 발생
     */
    @Transactional(readOnly = true)
    public Todo execute(GetTodoCommand getCommand) {
        return todoRepo.findAggregate(getCommand.id())
                .orElseThrow(() -> new BusinessException(TodoExceptionEnum.TODO_NOT_FOUND));
    }
}
