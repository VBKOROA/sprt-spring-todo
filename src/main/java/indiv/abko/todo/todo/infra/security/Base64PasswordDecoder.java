package indiv.abko.todo.todo.infra.security;

import indiv.abko.todo.todo.application.service.PasswordDecoder;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class Base64PasswordDecoder implements PasswordDecoder {
    private static final Base64.Decoder decoder = Base64.getDecoder();

    @Override
    public String decode(String encodedPassword) {
        return new String(decoder.decode(encodedPassword));
    }
}
