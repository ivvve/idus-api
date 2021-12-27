package com.idus.hw.acceptance.user;

import com.idus.hw.acceptance.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.idus.hw.acceptance.user.steps.UserAcceptanceTestSteps.회원_가입_요청을_한다;
import static com.idus.hw.acceptance.user.steps.UserAcceptanceTestSteps.회원_가입_요청이_성공한다;

@DisplayName("회원 Acceptance Test")
class UserAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원 가입 기능")
    @Test
    void userJoinTest() {
        // when
        var 회원_가입_응답 = 회원_가입_요청을_한다("tester@gmail.com", "Passw@rd123");

        // then
        회원_가입_요청이_성공한다(회원_가입_응답);
    }
}
