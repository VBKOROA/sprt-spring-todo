package indiv.abko.todo.global.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final BusinessExceptionEnum businessExceptionEnum;

    public BusinessException(BusinessExceptionEnum e) {
        super();
        this.businessExceptionEnum = e;
    }
}
