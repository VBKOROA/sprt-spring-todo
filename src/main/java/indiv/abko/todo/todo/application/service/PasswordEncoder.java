package indiv.abko.todo.todo.application.service;

import indiv.abko.todo.todo.domain.vo.Password;

public interface PasswordEncoder {
    Password encode(String rawPassword);
    boolean matches(String rawPassword, Password encodedPassword);
}
