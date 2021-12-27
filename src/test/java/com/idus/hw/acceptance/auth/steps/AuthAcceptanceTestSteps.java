package com.idus.hw.acceptance.auth.steps;

import com.idus.hw.acceptance.common.AuthToken;
import com.idus.hw.common.web.WebConstants;
import com.idus.hw.ui.web.auth.dto.LoginRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import static com.idus.hw.acceptance.common.ResponseAssertions.*;
import static com.idus.hw.acceptance.common.ResponseUtils.extractUserIdFrom;
import static com.idus.hw.acceptance.common.TestSessionUtils.setSession;
import static com.idus.hw.acceptance.user.steps.UserAcceptanceTestSteps.회원_가입이_되어있다;

public class AuthAcceptanceTestSteps {
    // given

    /**
     * @return User ID
     */
    public static long 회원_가입_후_로그인을_했다(String email, String password) {
        var userJoinResponse = 회원_가입이_되어있다(email, password);
        로그인을_했다(email, password);
        return extractUserIdFrom(userJoinResponse);
    }

    public static ExtractableResponse<Response> 로그인을_했다(String email, String password) {
        var loginResponse = 로그인_요청을_한다(email, password);
        로그인_요청이_성공한다(loginResponse);

        var authToken = 인증_토큰을_가져온다(loginResponse);
        인증_토큰을_저장한다(authToken);
        return loginResponse;
    }

    // when
    public static ExtractableResponse<Response> 로그인_요청을_한다(String email, String password) {
        var request = new LoginRequest();
        request.setEmail(email);
        request.setPassword(password);

        return RestAssured
                .given().body(request)
                .when().post("/auth/login")
                .then().extract();
    }

    // when
    public static ExtractableResponse<Response> 로그아웃_요청을_한다() {
        return RestAssured
                .given()
                .when().post("/auth/logout")
                .then().extract();
    }

    // then
    public static void 로그인_요청이_성공한다(ExtractableResponse<Response> loginResponse) {
        assertStatusCode(loginResponse, HttpStatus.OK);
        assertResponseIsSuccess(loginResponse);
    }

    public static void 로그아웃_요청이_성공한다(ExtractableResponse<Response> logoutResponse) {
        assertStatusCode(logoutResponse, HttpStatus.OK);
        assertResponseIsSuccess(logoutResponse);
    }

    public static AuthToken 인증_토큰을_가져온다(ExtractableResponse<Response> loginResponse) {
        var token = loginResponse.cookies().get(WebConstants.Session.COOKIE_NAME);
        return new AuthToken(token);
    }

    public static void 인증_토큰을_저장한다(AuthToken authToken) {
        setSession(authToken);
    }

    public static void 권한이_없어_요청이_실패한다(ExtractableResponse<Response> response) {
        assertStatusCode(response, HttpStatus.FORBIDDEN);
        assertResponseIsFalse(response);
    }

    public static void 권한이_확인되어_요청이_성공한다(ExtractableResponse<Response> response) {
        assertStatusCodeNotEquals(response, HttpStatus.FORBIDDEN);
    }
}
