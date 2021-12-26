package com.idus.hw.unit.core.user.application.auth;

import com.idus.hw.core.user.application.auth.AuthenticationService;
import com.idus.hw.core.user.domain.auth.exceptions.AuthenticationFailException;
import com.idus.hw.core.user.domain.user.entity.User;
import com.idus.hw.mock.core.user.domain.auth.MockPasswordEncoder;
import com.idus.hw.mock.core.user.domain.user.MockUserRepository;
import com.idus.hw.utils.user.UserTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("AuthenticationService 클래스")
class AuthenticationServiceTest {
    MockUserRepository userRepository = new MockUserRepository();
    MockPasswordEncoder passwordEncoder = new MockPasswordEncoder();
    AuthenticationService sut = new AuthenticationService(userRepository, passwordEncoder);

    @BeforeEach
    void beforeEach() {
        userRepository.reset();
    }

    private User testUser(String email) {
        return UserTestUtils.userBuilder()
                .email(email)
                .build();
    }

    @DisplayName("authenticate 메서드는")
    @Nested
    class authenticate {
        @DisplayName("유저 인증을 하고 인증된 유저의 User 객체를 리턴한다")
        @Test
        void authenticatesUser() {
            // given
            var user = userRepository.save(testUser("tester@gmail.com"));
            passwordEncoder.fixMatchResult(true);

            // when
            var authenticatedUser = sut.authenticate(user.getEmail(), "Passw@rd!");

            // then
            assertThat(authenticatedUser.getEmail()).isEqualTo("tester@gmail.com");
        }

        @DisplayName("입력된 email로 등록된 유저가 없는 경우 AuthenticationFailException을 throw 한다")
        @Test
        void failWhenUserNotRegistered() {
            // when & then
            assertThatThrownBy(() -> {
                sut.authenticate("tester@gmail.com", "Passw@rd!");
            }).isInstanceOf(AuthenticationFailException.class);
        }

        @DisplayName("입력된 password가 틀린 경우 AuthenticationFailException을 throw 한다")
        @Test
        void failWhenPasswordNotMatched() {
            // given
            passwordEncoder.fixMatchResult(false);
            var user = userRepository.save(testUser("tester@gmail.com"));

            // when & then
            assertThatThrownBy(() -> {
                sut.authenticate("tester@gmail.com", "Passw@rd!");
            }).isInstanceOf(AuthenticationFailException.class);
        }
    }
}