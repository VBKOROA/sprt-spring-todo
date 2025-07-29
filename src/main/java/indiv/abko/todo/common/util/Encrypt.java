package indiv.abko.todo.common.util;

import org.mapstruct.Named;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class Encrypt {
    private final String salt = BCrypt.gensalt();

    // @Named로 이름을 붙여서 "명시적으로 호출할 때만" 사용하도록 제한
    @Named("hashPassword")
    public String hash(String str) {
        return BCrypt.hashpw(str, salt);
    }

    public boolean compareHash(String original, String hashed) {
        return BCrypt.checkpw(original, hashed);
    }
}
