package indiv.abko.todo.todo.adapter.out.security;

import indiv.abko.todo.todo.domain.port.out.PasswordDecoder;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class Base64PasswordDecoder implements PasswordDecoder {
    private static final Base64.Decoder decoder = Base64.getDecoder();

    @Override
    public String decode(final String encodedPassword) {
        return new String(decoder.decode(encodedPassword));
    }
}
