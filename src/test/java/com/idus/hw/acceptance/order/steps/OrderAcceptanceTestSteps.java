package com.idus.hw.acceptance.order.steps;

import com.idus.hw.ui.web.order.dto.UserOrdersResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static com.idus.hw.acceptance.common.ResponseAssertions.assertResponseIsSuccess;
import static com.idus.hw.acceptance.common.ResponseAssertions.assertStatusCode;
import static com.idus.hw.acceptance.common.ResponseUtils.extractInnerDataFromResponse;

public class OrderAcceptanceTestSteps {
    // when
    public static ExtractableResponse<Response> 회원의_주문_목록_조회를_요청한다(long userId, int pageSize) {
        return RestAssured
                .given().queryParams(Map.of(
                        "pageSize", pageSize
                ))
                .when().get("/orders/users/{userId}", userId)
                .then().extract();
    }

    public static ExtractableResponse<Response> 회원의_다음_주문_목록_조회를_요청한다(long userId, long lastReadOrderId, int pageSize) {
        return RestAssured
                .given().queryParams(Map.of(
                        "pageSize", pageSize,
                        "lastReadOrderId", lastReadOrderId
                ))
                .when().get("/orders/users/{userId}", userId)
                .then().extract();
    }

    public static long 회원의_주문_목록의_마지막_주문_ID를_가져온다(ExtractableResponse<Response> userOrdersResponse) {
        var bodyData = extractInnerDataFromResponse(userOrdersResponse, UserOrdersResponse.class);
        var userOrders = bodyData.getContents();
        return userOrders.get(userOrders.size() - 1).getId();
    }

    // then
    public static void 회원의_주문_목록_조회가_성공한다(ExtractableResponse<Response> userOrdersResponse) {
        assertStatusCode(userOrdersResponse, HttpStatus.OK);
        assertResponseIsSuccess(userOrdersResponse);
    }
}
