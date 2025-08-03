package indiv.abko.todo.todo.application.port.out;

import indiv.abko.todo.todo.domain.vo.Password;

public interface PasswordEncoder {
    Password encode(final String rawPassword);
    boolean matches(final String rawPassword, final Password encodedPassword);
}
