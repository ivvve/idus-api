package com.idus.hw.unit.common.validation.checker;

import com.idus.hw.common.validation.checker.EmailFormatChecker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("EmailFormatChecker 클래스")
public class EmailFormatCheckerTest {
    @DisplayName("check 메서드는")
    @Nested
    class check {
        @DisplayName("입력이 이메일 형식인지 확인한다")
        @Test
        void returnsGivenValueIsEmailFormatOrNot() {
            // true
            assertThat(EmailFormatChecker.check("tester@gmail.com")).isTrue();

            // false
            assertThat(EmailFormatChecker.check("")).isFalse();
            assertThat(EmailFormatChecker.check("@")).isFalse();
            assertThat(EmailFormatChecker.check("tester@")).isFalse();
            assertThat(EmailFormatChecker.check("@gmail.com")).isFalse();
        }
    }
}
