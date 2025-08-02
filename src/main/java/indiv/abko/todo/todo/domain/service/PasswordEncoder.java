package indiv.abko.todo.todo.domain.service;

import indiv.abko.todo.global.vo.Password;

public interface PasswordEncoder {
    Password encode(String rawPassword);
    boolean matches(String rawPassword, Password encodedPassword);
}
