package indiv.abko.todo.todo.application.service;

import indiv.abko.todo.global.exception.BusinessException;
import indiv.abko.todo.todo.adapter.in.rest.dto.todo.TodoUpdateReq;
import indiv.abko.todo.todo.adapter.in.rest.mapper.TodoMapper;
import indiv.abko.todo.todo.application.port.out.PasswordDecoder;
import indiv.abko.todo.todo.application.port.out.PasswordEncoder;
import indiv.abko.todo.todo.application.port.out.TodoRepository;
import indiv.abko.todo.todo.domain.Todo;
import indiv.abko.todo.todo.domain.exception.TodoExceptionEnum;
import indiv.abko.todo.todo.domain.vo.Password;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @InjectMocks
    private TodoService todoService;

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PasswordDecoder passwordDecoder;

    @Mock
    private TodoMapper todoMapper;

    @Test
    @DisplayName("할일 수정 - 성공 케이스")
    void 비밀번호가일치하면_할일을성공적으로수정해야한다() {
        // given
        Long todoId = 1L;
        String rawPassword = "password1234";
        Password encodedPasswordVO = new Password("encodedPassword");

        TodoUpdateReq req = new TodoUpdateReq("수정된 제목", "수정된 작성자", rawPassword);
        Todo todo = Todo.builder().password(encodedPasswordVO).build();

        given(todoRepository.findAggregate(todoId)).willReturn(Optional.of(todo));
        given(passwordEncoder.matches(rawPassword, encodedPasswordVO)).willReturn(true);

        // when
        todoService.updateTodo(todoId, req);

        // then
        verify(todoRepository).save(any(Todo.class));
    }

    @Test
    @DisplayName("할일 수정 - 실패 케이스: 비밀번호 불일치")
    void 비밀번호가일치하지않으면_수정시예외가발생해야한다() {
        // given
        Long todoId = 1L;
        String wrongPassword = "wrongPassword";
        Password encodedPasswordVO = new Password("encodedPassword");

        TodoUpdateReq req = new TodoUpdateReq("수정된 제목", "수정된 작성자", wrongPassword);
        Todo todo = Todo.builder().password(encodedPasswordVO).build();

        given(todoRepository.findAggregate(todoId)).willReturn(Optional.of(todo));
        given(passwordEncoder.matches(wrongPassword, encodedPasswordVO)).willReturn(false);

        // when & then
        assertThatThrownBy(() -> todoService.updateTodo(todoId, req))
                .isInstanceOf(BusinessException.class)
                .satisfies(e -> assertThat(((BusinessException) e).getBusinessExceptionEnum())
                        .isEqualTo(TodoExceptionEnum.TODO_PERMISSION_DENIED));
    }

    @Test
    @DisplayName("할일 삭제 - 성공 케이스")
    void 비밀번호가일치하면_할일을성공적으로삭제해야한다() {
        // given
        Long todoId = 1L;
        String rawPassword = "password1234";
        String encodedPassword = "encodedPassword";
        Password encodedPasswordVO = new Password("encodedPassword");
        Todo todo = Todo.builder().password(encodedPasswordVO).build();

        given(todoRepository.findSummary(todoId)).willReturn(Optional.of(todo));
        given(passwordDecoder.decode(encodedPassword)).willReturn(rawPassword);
        given(passwordEncoder.matches(rawPassword, encodedPasswordVO)).willReturn(true);

        // when
        todoService.deleteTodo(todoId, encodedPassword);

        // then
        verify(todoRepository).delete(todo);
    }

    @Test
    @DisplayName("할일 삭제 - 실페 케이스: 비밀번호 불일치")
    void 비밀번호가일치하지않으면_삭제시예외가발생해야한다() {
        // given
        Long todoId = 1L;
        String wrongRawPassword = "wrongPassword";
        String wrongEncodedPassword = "wrongEncodedPassword";
        Password encodedPasswordVO = new Password("encodedPassword");
        Todo todo = Todo.builder().password(encodedPasswordVO).build();

        given(todoRepository.findSummary(todoId)).willReturn(Optional.of(todo));
        given(passwordDecoder.decode(wrongEncodedPassword)).willReturn(wrongRawPassword);
        given(passwordEncoder.matches(wrongRawPassword, encodedPasswordVO)).willReturn(false);

        // when & then
        assertThatThrownBy(() -> todoService.deleteTodo(todoId, wrongEncodedPassword))
                .isInstanceOf(BusinessException.class)
                .satisfies(e -> assertThat(((BusinessException) e).getBusinessExceptionEnum()).isEqualTo(TodoExceptionEnum.TODO_PERMISSION_DENIED));
    }
}
