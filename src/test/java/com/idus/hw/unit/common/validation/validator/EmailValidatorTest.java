package com.idus.hw.unit.common.validation.validator;

import com.idus.hw.common.validation.validator.EmailValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("EmailValidator 클래스")
class EmailValidatorTest {
    @DisplayName("isValid 메서드는")
    @Nested
    class isValid {
        @DisplayName("입력이 이메일 형식인지 확인한다")
        @Test
        void returnsGivenValueIsEmailFormatOrNot() {
            // true
            assertThat(EmailValidator.isValid("tester@gmail.com")).isTrue();

            // false
            assertThat(EmailValidator.isValid("")).isFalse();
            assertThat(EmailValidator.isValid("@")).isFalse();
            assertThat(EmailValidator.isValid("tester@")).isFalse();
            assertThat(EmailValidator.isValid("@gmail.com")).isFalse();
        }
    }
}