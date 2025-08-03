package indiv.abko.todo.global.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private BusinessExceptionEnum businessExceptionEnum = GlobalExceptionEnum.UNKNOWN_ERROR;
    private Object data;

    public BusinessException(BusinessExceptionEnum e) {
        super();
        this.businessExceptionEnum = e;
    }
}
