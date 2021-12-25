package com.idus.hw.common.validation.validator;

import com.idus.hw.common.validation.annotation.PhoneNumberFormat;
import com.idus.hw.common.validation.checker.PhoneNumberFormatChecker;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberFormatValidator implements ConstraintValidator<PhoneNumberFormat, String> {
    private boolean nullable = false;

    @Override
    public void initialize(PhoneNumberFormat constraintAnnotation) {
        this.nullable = constraintAnnotation.nullable();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (nullable) {
            return true;
        }

        return PhoneNumberFormatChecker.check(value);
    }
}
