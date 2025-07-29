package indiv.abko.todo.common.exception;

import java.util.Optional;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private ExceptionEnum e = ExceptionEnum.UNKNOWN_ERROR;
    private Optional<Object> data = Optional.empty();

    public BusinessException() {
        super();
    }

    public BusinessException(ExceptionEnum e) {
        super();
        this.e = e;
        this.data = Optional.empty();
    }

    public BusinessException(ExceptionEnum e, Object data) {
        super();
        this.e = e;
        this.data = Optional.ofNullable(data);
    }
}
