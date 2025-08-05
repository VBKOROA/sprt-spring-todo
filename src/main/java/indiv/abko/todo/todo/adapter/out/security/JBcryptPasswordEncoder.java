package indiv.abko.todo.todo.adapter.out.security;

import indiv.abko.todo.todo.domain.vo.PasswordVO;
import indiv.abko.todo.todo.domain.port.out.PasswordEncoder;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class JBcryptPasswordEncoder implements PasswordEncoder {
    @Override
    public PasswordVO encode(final String rawPassword) {
        return new PasswordVO(BCrypt.hashpw(rawPassword, BCrypt.gensalt()));
    }

    @Override
    public boolean matches(final String rawPassword, final PasswordVO password) {
        return BCrypt.checkpw(rawPassword, password.getPassword());
    }
}
