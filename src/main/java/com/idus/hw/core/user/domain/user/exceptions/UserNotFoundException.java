package com.idus.hw.core.user.domain.user.exceptions;

import com.idus.hw.common.exceptions.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(long userId) {
        super("User with the id not found: " + userId);
    }
}
