package com.idus.hw.acceptance.user.steps;

import com.idus.hw.core.user.domain.user.entity.Gender;
import com.idus.hw.ui.web.user.dto.UserJoinRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import static com.idus.hw.acceptance.common.ResponseAssertions.assertResponseIsSuccess;
import static com.idus.hw.acceptance.common.ResponseAssertions.assertStatusCode;

public class UserAcceptanceTestSteps {
    // given
    public static ExtractableResponse<Response> 회원_가입이_되어있다(String email, String password) {
        var response = 회원_가입_요청을_한다(email, password);
        회원_가입_요청이_성공한다(response);
        return response;
    }

    // when
    public static ExtractableResponse<Response> 회원_가입_요청을_한다(String email, String password) {
        var request = new UserJoinRequest();
        request.setEmail(email);
        request.setPassword(password);
        request.setName("테스터");
        request.setNickname("tester");
        request.setPhoneNumber("01011112222");
        request.setGender(Gender.MALE.name());

        return RestAssured
                .given().body(request)
                .when().post("/users")
                .then().extract();
    }

    public static ExtractableResponse<Response> 회원_정보_조회_요청을_한다(long userId) {
        return RestAssured
                .given()
                .when().get("/users/{userId}", userId)
                .then().extract();
    }

    // then
    public static void 회원_가입_요청이_성공한다(ExtractableResponse<Response> userJoinResponse) {
        assertStatusCode(userJoinResponse, HttpStatus.CREATED);
        assertResponseIsSuccess(userJoinResponse);
    }
}
