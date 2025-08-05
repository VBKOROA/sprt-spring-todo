package indiv.abko.todo.todo.domain.service;

import org.springframework.stereotype.Component;
import indiv.abko.todo.global.exception.BusinessException;
import indiv.abko.todo.todo.application.port.out.PasswordEncoder;
import indiv.abko.todo.todo.domain.Todo;
import indiv.abko.todo.todo.domain.exception.TodoExceptionEnum;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TodoAuthValidator {
    private final PasswordEncoder passwordEncoder;

    public void shouldHaveAuth(final Todo todo, final String rawPassword) {
        if(todo.getPassword().matches(rawPassword, passwordEncoder) == false) {
            throw new BusinessException(TodoExceptionEnum.TODO_PERMISSION_DENIED);
        }
    }
}
