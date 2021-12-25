package com.idus.hw.common.validation.validator;

import com.idus.hw.common.validation.annotation.EmailFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class EmailFormatValidator implements ConstraintValidator<EmailFormat, String> {
    private static final String REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    public static boolean isValid(String mayEmail) {
        return PATTERN.matcher(mayEmail).matches();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isValid(value);
    }
}
