package com.idus.hw.acceptance.user;

import com.idus.hw.acceptance.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.idus.hw.acceptance.auth.steps.AuthAcceptanceTestSteps.회원_가입_후_로그인을_했다;
import static com.idus.hw.acceptance.user.steps.SearchUsersAcceptanceTestSteps.*;
import static com.idus.hw.acceptance.user.steps.UserAcceptanceTestSteps.회원_가입이_되어있다;

@DisplayName("회원 검색 Acceptance Test")
public class SearchUsersAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원 검색 기능")
    @Test
    void searchUsers() {
        // given
        회원_가입이_되어있다("tester2@gmail.com", "Passw@rd123");
        회원_가입_후_로그인을_했다("tester@gmail.com", "Passw@rd123");

        // when
        var 회원_검색_응답 = 회원_검색을_요청한다(1, 50);

        // then
        회원_검색이_성공한다(회원_검색_응답);
        회원이_검색된다(회원_검색_응답, "tester@gmail.com", "tester2@gmail.com");
    }
}
