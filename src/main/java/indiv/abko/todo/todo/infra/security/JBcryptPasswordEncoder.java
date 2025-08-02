package indiv.abko.todo.todo.infra.security;

import indiv.abko.todo.todo.domain.vo.Password;
import indiv.abko.todo.todo.application.service.PasswordEncoder;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class JBcryptPasswordEncoder implements PasswordEncoder {
    public Password encode(final String rawPassword) {
        return new Password(BCrypt.hashpw(rawPassword, BCrypt.gensalt()));
    }

    public boolean matches(final String rawPassword, final Password password) {
        return BCrypt.checkpw(rawPassword, password.getPassword());
    }
}
