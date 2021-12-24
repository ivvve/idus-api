package com.idus.hw.common.exceptions;

/**
 * 외부 인프라스트럭처 연동 오류
 */
public class IntegrationException extends BaseException {
    public IntegrationException(String message) {
        super(message);
    }

    public IntegrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public IntegrationException(Throwable cause) {
        super(cause);
    }
}
