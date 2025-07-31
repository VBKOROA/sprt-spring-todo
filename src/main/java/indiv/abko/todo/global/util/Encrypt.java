package indiv.abko.todo.global.util;

import org.mapstruct.Named;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class Encrypt {
    // @Named로 이름을 붙여서 "명시적으로 호출할 때만" 사용하도록 제한
    @Named("hashPassword")
    public String hash(final String str) {
        return BCrypt.hashpw(str, BCrypt.gensalt());
    }

    public boolean isHashEqual(final String original, final String hashed) {
        return BCrypt.checkpw(original, hashed);
    }
}
