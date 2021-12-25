package com.idus.hw.common.validation.validator;

import com.idus.hw.common.validation.annotation.UserNicknameFormat;
import com.idus.hw.common.validation.checker.UserNicknameFormatChecker;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserNicknameFormatValidator implements ConstraintValidator<UserNicknameFormat, String> {
    private boolean nullable = false;

    @Override
    public void initialize(UserNicknameFormat constraintAnnotation) {
        this.nullable = constraintAnnotation.nullable();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (nullable) {
            return true;
        }

        return UserNicknameFormatChecker.check(value);
    }
}
