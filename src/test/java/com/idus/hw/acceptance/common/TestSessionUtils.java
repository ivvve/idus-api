package com.idus.hw.acceptance.common;

import io.restassured.RestAssured;

public class TestSessionUtils {
    /**
     * RestAssured.sessionId 만 변경하는 것은 RestAssured가 요청 시
     * 내부에서 사용하는 SessionConfig가 변경되지 않기 때문에 효과가 없다.
     * 그렇기 때문에 RestAssured.requestSpecification를 변경해야한다
     */
    public static void setSession(AuthToken authToken) {
        RestAssured.requestSpecification =
                RestAssured.requestSpecification.sessionId(authToken.getToken());
    }

    public static void clearSession() {
        RestAssured.requestSpecification =
                RestAssured.requestSpecification.sessionId("");
    }
}
