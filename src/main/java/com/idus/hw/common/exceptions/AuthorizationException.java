package com.idus.hw.common.exceptions;

/**
 * 인가 관련 오류
 * e.g. 인증이 되지 않은 상태에서 요청을 할 때
 * 타인의 개인 정보를 요청할 때
 **/
public class AuthorizationException extends BaseException {
    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorizationException(Throwable cause) {
        super(cause);
    }
}
