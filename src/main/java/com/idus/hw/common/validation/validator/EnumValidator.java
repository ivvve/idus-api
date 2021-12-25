package com.idus.hw.common.validation.validator;

import com.idus.hw.common.validation.annotation.EnumFormat;
import com.idus.hw.common.validation.checker.EnumFormatChecker;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<EnumFormat, String> {
    private boolean nullable = false;
    private Class<? extends Enum> enumClass;

    @Override
    public void initialize(EnumFormat constraintAnnotation) {
        this.nullable = constraintAnnotation.nullable();
        enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (nullable) {
            return true;
        }

        return EnumFormatChecker.check(enumClass, value);
    }
}
