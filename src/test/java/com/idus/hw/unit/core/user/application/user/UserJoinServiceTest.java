package com.idus.hw.unit.core.user.application.user;

import com.idus.hw.core.user.application.user.UserJoinService;
import com.idus.hw.core.user.domain.user.entity.Gender;
import com.idus.hw.mock.core.user.domain.auth.MockPasswordEncoder;
import com.idus.hw.mock.core.user.domain.user.MockUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UserJoinService 클래스")
class UserJoinServiceTest {
    MockUserRepository userRepository = new MockUserRepository();
    MockPasswordEncoder passwordEncoder = new MockPasswordEncoder();
    UserJoinService sut = new UserJoinService(userRepository, passwordEncoder);

    @BeforeEach
    void beforeEach() {
        userRepository.reset();
    }

    @Nested
    class join {
        @DisplayName("새로운 회원을 가입시킨다 - 성별을 입력하지 않는 경우")
        @Test
        void joinWithoutGender() {
            // when
            var joinedUser = sut.join("tester@gmail.com", "Passssw@rd!", "가나다", "hello", "01011112222", null);

            // then
            assertThat(joinedUser.getEmail()).isEqualTo("tester@gmail.com");
            assertThat(joinedUser.getEncodedPassword()).isNotBlank();
            assertThat(joinedUser.getName()).isEqualTo("가나다");
            assertThat(joinedUser.getNickname()).isEqualTo("hello");
            assertThat(joinedUser.getPhoneNumber()).isEqualTo("01011112222");
            assertThat(joinedUser.getGender()).isEqualTo(null);
        }

        @DisplayName("새로운 회원을 가입시킨다 - 성별을 같이 입력한 경우")
        @Test
        void joinWithGender() {
            // when
            var joinedUser = sut.join("tester@gmail.com", "Passssw@rd!", "가나다", "hello", "01011112222", Gender.FEMALE);

            // then
            assertThat(joinedUser.getEmail()).isEqualTo("tester@gmail.com");
            assertThat(joinedUser.getEncodedPassword()).isNotBlank();
            assertThat(joinedUser.getName()).isEqualTo("가나다");
            assertThat(joinedUser.getNickname()).isEqualTo("hello");
            assertThat(joinedUser.getPhoneNumber()).isEqualTo("01011112222");
            assertThat(joinedUser.getGender()).isEqualTo(Gender.FEMALE);
        }
    }
}