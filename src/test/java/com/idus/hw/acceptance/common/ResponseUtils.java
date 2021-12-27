package com.idus.hw.acceptance.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idus.hw.common.web.dto.BaseResponse;
import com.idus.hw.ui.web.user.dto.UserJoinResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import lombok.SneakyThrows;

public class ResponseUtils {
    @SneakyThrows
    public static <T> T extractInnerDataFromResponse(ExtractableResponse<Response> response, Class<T> innerDataClass) {
        var objectMapper = new ObjectMapper();
        return objectMapper.readValue(
                objectMapper.writeValueAsString(extractBaseResponse(response).getData()),
                innerDataClass
        );
    }

    public static <T> BaseResponse<T> extractBaseResponse(ExtractableResponse<Response> response) {
        return (BaseResponse<T>) response.as(BaseResponse.class);
    }

    public static long extractUserIdFrom(ExtractableResponse<Response> userJoinResponse) {
        return extractInnerDataFromResponse(userJoinResponse, UserJoinResponse.class).getId();
    }
}
