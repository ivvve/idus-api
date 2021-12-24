package com.idus.hw.core.user.domain.user.exceptions;

import com.idus.hw.common.exceptions.IntegrationException;

public class UserRepositoryIntegrationException extends IntegrationException {
    public UserRepositoryIntegrationException(Throwable cause) {
        super(cause);
    }
}
