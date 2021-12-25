package com.idus.hw.common.exceptions;

/**
 * 새로 만드려는 리소스가 다른 리소스와 중복될 때 오류
 */
public class ResourceDuplicationException extends BaseException {
    public ResourceDuplicationException(String message) {
        super(message);
    }

    public ResourceDuplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceDuplicationException(Throwable cause) {
        super(cause);
    }
}
