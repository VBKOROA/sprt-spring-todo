package indiv.abko.todo.todo.application.usecase;

import indiv.abko.todo.todo.domain.port.out.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import indiv.abko.todo.todo.application.port.in.command.CreateTodoCommand;
import indiv.abko.todo.todo.domain.port.out.TodoRepository;
import indiv.abko.todo.todo.domain.Todo;
import indiv.abko.todo.todo.domain.vo.ContentVO;
import indiv.abko.todo.todo.domain.vo.TodoTitleVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateTodoUseCase {
    private final TodoRepository todoRepo;
    private final PasswordEncoder passwordEncoder;

    /**
     * 새로운 Todo 항목을 생성하고 저장소에 저장한다.
     *
     * @param createCommand 새 Todo의 정보를 담은 명령 객체 (작성자, 제목, 내용, 비밀번호 포함)
     * @return 저장된 Todo 엔티티
     */
    @Transactional
    public Todo execute(final CreateTodoCommand createCommand) {
        final var encodedPassword = passwordEncoder.encode(createCommand.password());
        final Todo todo = Todo.builder().author(createCommand.author())
                .content(new ContentVO(createCommand.content()))
                .title(new TodoTitleVO(createCommand.title()))
                .password(encodedPassword).build();
        return todoRepo.save(todo);
    }
}
