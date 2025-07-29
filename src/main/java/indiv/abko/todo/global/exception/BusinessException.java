package indiv.abko.todo.global.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private ExceptionEnum exceptionEnum = ExceptionEnum.UNKNOWN_ERROR;
    private Object data;

    public BusinessException() {
        super();
    }

    public BusinessException(ExceptionEnum e) {
        super();
        this.exceptionEnum = e;
    }

    public BusinessException(ExceptionEnum e, Object data) {
        super();
        this.exceptionEnum = e;
        this.data = data;
    }
}
