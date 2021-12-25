package com.idus.hw.common.validation.validator;

import com.idus.hw.common.validation.annotation.PasswordFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordFormatValidator implements ConstraintValidator<PasswordFormat, String> {
    private static final int PASSWORD_MIN_LENGTH = 10;
    private static final String SPECIAL_CHARACTERS = "/*!@#$%^&*()\\\"{}_[]|\\?/<>,.";

    public static boolean isValid(String mayPassword) {
        if (!isValidLength(mayPassword)) {
            return false;
        }

        if (!containsAllRequiredCharacters(mayPassword)) {
            return false;
        }

        if (containsUnsupportedCharacter(mayPassword)) {
            return false;
        }

        return true;
    }

    private static boolean isValidLength(String mayPassword) {
        return PASSWORD_MIN_LENGTH <= mayPassword.length();
    }

    private static boolean containsAllRequiredCharacters(String mayPassword) {
        var alphabetUpper = false;
        var alphabetLower = false;
        var number = false;
        var specialCharacter = false;

        for (char c : mayPassword.toCharArray()) {
            alphabetUpper = alphabetUpper || Character.isUpperCase(c);
            alphabetLower = alphabetLower || Character.isLowerCase(c);
            number = number || Character.isDigit(c);
            specialCharacter = specialCharacter || isSpecialCharacter(c);

            if (alphabetUpper && alphabetLower && number && specialCharacter) {
                return true;
            }
        }

        return false;
    }

    private static boolean containsUnsupportedCharacter(String mayPassword) {
        for (char c : mayPassword.toCharArray()) {
            if (!isSupportedCharacter(c)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isSupportedCharacter(char c) {
        return Character.isUpperCase(c) ||
                Character.isLowerCase(c) ||
                Character.isDigit(c) ||
                isSpecialCharacter(c);
    }

    private static boolean isSpecialCharacter(char c) {
        return 0 <= SPECIAL_CHARACTERS.indexOf(c);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isValid(value);
    }
}
