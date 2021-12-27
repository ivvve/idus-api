package com.idus.hw.config.swagger;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.ZoneId;

import static springfox.documentation.builders.RequestHandlerSelectors.*;

@Configuration
public class SwaggerUIConfiguration {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .useDefaultResponseMessages(false)
                .select()
                .apis(basePackage("com.idus.hw.ui.web")
                        .and(withClassAnnotation(RestController.class))
                        .and(withMethodAnnotation(ApiOperation.class))
                )
                .paths(PathSelectors.any())
                .build()
                .ignoredParameterTypes(ZoneId.class)
                .apiInfo(this.apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API documentation")
                .description("API documentation")
                .version("1.0")
                .build();
    }
}
