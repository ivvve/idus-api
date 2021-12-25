package com.idus.hw;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {
    private static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.0");
    @LocalServerPort
    private int serverPort;

    @DynamicPropertySource
    public static void registerMySQLProperties(DynamicPropertyRegistry propertyRegistry) {
        if (mySQLContainer.isRunning()) {
            return;
        }

        startMySQLContainer(mySQLContainer);
        setMySQLProperties(mySQLContainer, propertyRegistry);
    }

    private static void startMySQLContainer(MySQLContainer mySQLContainer) {
        log.info("MySQL Test Container is getting started");

        mySQLContainer
                .withUsername("root")
                .withPassword("root")
                .withDatabaseName("idus")
                .withReuse(true)
                .start();

        log.info("MySQL Test Container is now running");
    }

    private static void setMySQLProperties(MySQLContainer mySQLContainer,
                                           DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.write.hikari.jdbc-url", mySQLContainer::getJdbcUrl);
        propertyRegistry.add("spring.datasource.write.hikari.username", mySQLContainer::getUsername);
        propertyRegistry.add("spring.datasource.write.hikari.password", mySQLContainer::getPassword);

        propertyRegistry.add("spring.datasource.read-only.hikari.jdbc-url", mySQLContainer::getJdbcUrl);
        propertyRegistry.add("spring.datasource.read-only.hikari.username", mySQLContainer::getUsername);
        propertyRegistry.add("spring.datasource.read-only.hikari.password", mySQLContainer::getPassword);

        propertyRegistry.add("spring.flyway.url", mySQLContainer::getJdbcUrl);
        propertyRegistry.add("spring.flyway.user", mySQLContainer::getUsername);
        propertyRegistry.add("spring.flyway.password", mySQLContainer::getPassword);
    }

    @BeforeEach
    void beforeEach() {
        RestAssured.port = serverPort;
    }
}
