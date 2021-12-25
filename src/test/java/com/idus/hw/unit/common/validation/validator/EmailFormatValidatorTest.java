package com.idus.hw.unit.common.validation.validator;

import com.idus.hw.common.validation.validator.EmailFormatValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("EmailFormatValidator 클래스")
class EmailFormatValidatorTest {
    @DisplayName("isValid 메서드는")
    @Nested
    class isValid {
        @DisplayName("입력이 이메일 형식인지 확인한다")
        @Test
        void returnsGivenValueIsEmailFormatOrNot() {
            // true
            assertThat(EmailFormatValidator.isValid("tester@gmail.com")).isTrue();

            // false
            assertThat(EmailFormatValidator.isValid("")).isFalse();
            assertThat(EmailFormatValidator.isValid("@")).isFalse();
            assertThat(EmailFormatValidator.isValid("tester@")).isFalse();
            assertThat(EmailFormatValidator.isValid("@gmail.com")).isFalse();
        }
    }
}