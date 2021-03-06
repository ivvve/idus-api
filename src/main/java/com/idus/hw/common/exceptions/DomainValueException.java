package com.idus.hw.common.exceptions;

/**
 * 특정 도메인의 유효하지 값에 대한 오류
 */
public class DomainValueException extends BaseException {
    public DomainValueException(String message) {
        super(message);
    }

    public DomainValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public DomainValueException(Throwable cause) {
        super(cause);
    }
}
