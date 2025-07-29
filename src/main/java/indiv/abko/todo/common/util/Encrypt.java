package indiv.abko.todo.common.util;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class Encrypt {
    public String hash(String str) {
        return BCrypt.hashpw(str, BCrypt.gensalt());
    }

    public boolean compareHash(String original, String hashed) {
        return BCrypt.checkpw(original, hashed);
    }
}
