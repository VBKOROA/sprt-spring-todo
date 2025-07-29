package indiv.abko.todo.common.util;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class Encrypt {
    private String hash(String str) {
        return BCrypt.hashpw(str, BCrypt.gensalt());
    }

    private boolean compareHash(String original, String hashed) {
        return BCrypt.checkpw(original, hashed);
    }
}
