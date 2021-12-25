package com.idus.hw.core.user.domain.user.exceptions;

import com.idus.hw.common.exceptions.ResourceDuplicationException;

public class UserDuplicationException extends ResourceDuplicationException {
    private UserDuplicationException(String message) {
        super(message);
    }

    public static UserDuplicationException duplicatedEmail(String email) {
        return new UserDuplicationException("User email is duplicated: " + email);
    }
}
