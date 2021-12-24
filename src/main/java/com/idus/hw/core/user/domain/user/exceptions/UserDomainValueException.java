package com.idus.hw.core.user.domain.user.exceptions;

import com.idus.hw.common.exceptions.DomainValueException;

public class UserDomainValueException extends DomainValueException {
    public UserDomainValueException(String fieldName, Object value) {
        super("User domain field '" + fieldName + "' doesn't support given value: " + value);
    }
}
