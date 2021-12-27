package com.idus.hw.acceptance.auth;

import com.idus.hw.acceptance.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.idus.hw.acceptance.auth.steps.AuthAcceptanceTestSteps.*;
import static com.idus.hw.acceptance.user.steps.UserAcceptanceTestSteps.회원_가입이_되어있다;
import static com.idus.hw.acceptance.user.steps.UserAcceptanceTestSteps.회원_정보_조회_요청을_한다;

@DisplayName("인증 Acceptance Test")
public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("인증 기능")
    @Test
    void authTest() {
        // given
        회원_가입이_되어있다("tester@gmail.com", "Passw@rd123");

        // when
        var 회원_정보_조회_응답 = 회원_정보_조회_요청을_한다(1L);
        // then
        권한이_없어_요청이_실패한다(회원_정보_조회_응답);

        // when
        var 로그인_응답 = 로그인_요청을_한다("tester@gmail.com", "Passw@rd123");
        // then
        로그인_요청이_성공한다(로그인_응답);
        var 인증_토큰 = 인증_토큰을_가져온다(로그인_응답);
        인증_토큰을_저장한다(인증_토큰);

        // when
        회원_정보_조회_응답 = 회원_정보_조회_요청을_한다(1L);
        // then
        권한이_확인되어_요청이_성공한다(회원_정보_조회_응답);

        // when
        var 로그아웃_응답 = 로그아웃_요청을_한다();
        // then
        로그아웃_요청이_성공한다(로그아웃_응답);
    }
}
