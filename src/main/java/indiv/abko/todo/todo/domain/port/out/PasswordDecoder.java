package indiv.abko.todo.todo.domain.port.out;

public interface PasswordDecoder {
    String decode(final String encodedPassword);
}
