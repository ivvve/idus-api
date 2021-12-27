package com.idus.hw.acceptance;

import com.idus.hw.acceptance.common.TestSessionUtils;
import com.idus.hw.integration.IntegrationTest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.web.server.LocalServerPort;

@Slf4j
public abstract class AcceptanceTest extends IntegrationTest {
    static {
        RestAssured.filters(
                new RequestLoggingFilter(),
                new ResponseLoggingFilter()
        );
    }

    @LocalServerPort
    private int serverPort;

    @BeforeEach
    void beforeEach_() { // IntegrationTest#beforeEach 와 다르게 네이밍
        this.setDefaultRequestSpecificationIfNotset();
        this.clearSession();
    }

    private void setDefaultRequestSpecificationIfNotset() {
        if (RestAssured.requestSpecification == null) {
            RestAssured.requestSpecification = new RequestSpecBuilder()
                    .setPort(serverPort)
                    .setContentType(ContentType.JSON)
                    .build();

            log.info("Set default RequestSpecification for RestAssured ");
        }
    }

    private void clearSession() {
        TestSessionUtils.clearSession();
        log.info("Session cleared");
    }
}
