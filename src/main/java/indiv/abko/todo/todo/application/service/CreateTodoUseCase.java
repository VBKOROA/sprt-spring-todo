package indiv.abko.todo.todo.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import indiv.abko.todo.todo.application.port.in.command.CreateTodoCommand;
import indiv.abko.todo.todo.application.port.out.TodoRepository;
import indiv.abko.todo.todo.domain.Todo;
import indiv.abko.todo.todo.domain.vo.Content;
import indiv.abko.todo.todo.domain.vo.Password;
import indiv.abko.todo.todo.domain.vo.TodoTitle;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateTodoUseCase {
    private final TodoRepository todoRepo;

    /**
     * 새로운 Todo 항목을 생성하고 저장소에 저장한다.
     *
     * @param createCommand 새 Todo의 정보를 담은 명령 객체 (작성자, 제목, 내용, 비밀번호 포함)
     * @return 저장된 Todo 엔티티
     */
    @Transactional
    public Todo execute(final CreateTodoCommand createCommand) {
        final Todo todo = Todo.builder().author(createCommand.author())
                .content(new Content(createCommand.content()))
                .title(new TodoTitle(createCommand.title()))
                .password(new Password(createCommand.password())).build();
        return todoRepo.save(todo);
    }
}
