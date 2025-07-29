package indiv.abko.todo.common.exception;

public class BusinessException extends RuntimeException {
    private ExceptionEnum message;

    public BusinessException() {
        super();
        this.message = ExceptionEnum.UNKNOWN_ERROR;
    }

    public BusinessException(ExceptionEnum message) {
        this.message = message;
    }
}
