package indiv.abko.todo.todo.domain.port.out;

import indiv.abko.todo.todo.domain.vo.PasswordVO;

public interface PasswordEncoder {
    PasswordVO encode(final String rawPassword);
    boolean matches(final String rawPassword, final PasswordVO encodedPassword);
}
