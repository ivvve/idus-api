package com.idus.hw.common.validation.validator;

import com.idus.hw.common.validation.annotation.UserNameFormat;
import com.idus.hw.common.validation.checker.UserNameFormatChecker;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserNameFormatValidator implements ConstraintValidator<UserNameFormat, String> {
    private boolean nullable = false;

    @Override
    public void initialize(UserNameFormat constraintAnnotation) {
        this.nullable = constraintAnnotation.nullable();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (nullable) {
            return true;
        }

        return UserNameFormatChecker.check(value);
    }
}
