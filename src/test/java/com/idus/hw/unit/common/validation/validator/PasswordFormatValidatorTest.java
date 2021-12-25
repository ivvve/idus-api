package com.idus.hw.unit.common.validation.validator;

import com.idus.hw.common.validation.validator.PasswordFormatValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PasswordFormatValidator 클래스")
class PasswordFormatValidatorTest {
    @DisplayName("isValid 메서드는")
    @Nested
    class isValid {
        @DisplayName("10자 이상이고 영문 대문자, 영문 소문자, 특수 문자, 숫자 각 1개 이상씩 포함되었는지 확인한다")
        @Test
        void returnsGivenValueIsPasswordFormat() {
            Assertions.assertThat(PasswordFormatValidator.isValid("AAAaaa!!11")).isTrue();
            // 대문자 1개
            assertThat(PasswordFormatValidator.isValid("Aaaaaa!!11")).isTrue();
            // 소문자 1개
            assertThat(PasswordFormatValidator.isValid("AAAAAa!!11")).isTrue();
            // 특수문자 1개
            assertThat(PasswordFormatValidator.isValid("AAAaaa!111")).isTrue();
            // 숫자 1개
            assertThat(PasswordFormatValidator.isValid("AAAaaa!!!1")).isTrue();
        }

        @DisplayName("10자 미만인 경우 false를 리턴한다")
        @Test
        void returnsFalseIfLengthIsLessThan10() {
            // 9자
            Assertions.assertThat(PasswordFormatValidator.isValid("AAAaaa!!1")).isFalse();
        }

        @DisplayName("영문 대문자 미포함인 경우 false를 리턴한다")
        @Test
        void returnsFalseIfNotContainsUpperCase() {
            Assertions.assertThat(PasswordFormatValidator.isValid("bbbaaa!!11")).isFalse();

        }

        @DisplayName("영문 소문자 미포함인 경우 false를 리턴한다")
        @Test
        void returnsFalseIfNotContainsLowerCase() {
            Assertions.assertThat(PasswordFormatValidator.isValid("AAAAAA!!11")).isFalse();

        }

        @DisplayName("특수문자 미포함인 경우 false를 리턴한다")
        @Test
        void returnsFalseIfNotContainsSpecialCharacter() {
            Assertions.assertThat(PasswordFormatValidator.isValid("AAAaaa1111")).isFalse();

        }

        @DisplayName("숫자 미포함인 경우 false를 리턴한다")
        @Test
        void returnsFalseIfNotContainsNumber() {
            Assertions.assertThat(PasswordFormatValidator.isValid("AAAaaa!!!!")).isFalse();

        }

        @DisplayName("영문, 특수 문자, 숫자 외 문자 포함인 경우 false를 리턴한다")
        @Test
        void returnsFalseIfContainsUnsupportedCharacter() {
            // 한글
            Assertions.assertThat(PasswordFormatValidator.isValid("ㅁAAaaa11!!")).isFalse();
            // 공백
            Assertions.assertThat(PasswordFormatValidator.isValid(" AAaaa11!!")).isFalse();
        }
    }
}