package com.idus.hw.acceptance.user.steps;

import com.idus.hw.ui.web.user.dto.SearchUsersResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static com.idus.hw.acceptance.common.ResponseAssertions.assertResponseIsSuccess;
import static com.idus.hw.acceptance.common.ResponseAssertions.assertStatusCode;
import static com.idus.hw.acceptance.common.ResponseUtils.extractInnerDataFromResponse;
import static org.assertj.core.api.Assertions.assertThat;

public class SearchUsersAcceptanceTestSteps {
    // when
    public static ExtractableResponse<Response> 회원_검색을_요청한다(int page, int pageSize) {
        return RestAssured
                .given().queryParams(Map.of(
                        "page", page,
                        "pageSize", pageSize
                ))
                .when().get("/users/search")
                .then().extract();
    }

    // then
    public static void 회원_검색이_성공한다(ExtractableResponse<Response> searchUsersResponse) {
        assertStatusCode(searchUsersResponse, HttpStatus.OK);
        assertResponseIsSuccess(searchUsersResponse);
    }

    public static void 회원이_검색된다(ExtractableResponse<Response> searchUsersResponse, String... emails) {
        var bodyData = extractInnerDataFromResponse(searchUsersResponse, SearchUsersResponse.class);
        assertThat(bodyData.getContents()).extracting(SearchUsersResponse.UserAndLatestOrder::getEmail)
                .containsExactlyInAnyOrder(emails);
    }
}
