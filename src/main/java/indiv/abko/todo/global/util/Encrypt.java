package indiv.abko.todo.global.util;

import indiv.abko.todo.global.vo.Password;
import indiv.abko.todo.todo.domain.service.PasswordEncoder;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class Encrypt implements PasswordEncoder {
    public Password encode(final String rawPassword) {
        return new Password(BCrypt.hashpw(rawPassword, BCrypt.gensalt()));
    }

    public boolean matches(final String rawPassword, final Password password) {
        return BCrypt.checkpw(rawPassword, password.getPassword());
    }
}
