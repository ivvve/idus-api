package com.idus.hw.config.web;

import com.idus.hw.common.web.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Configuration
@RestControllerAdvice
public class WebExceptionHandler {
    // TODO Exception 타입에 따라 응답 코드 등 변경 필요
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<BaseResponse<Void>> exceptionHandler(Exception e) {
        log.error("Exception captured", e);

        return ResponseEntity
                .internalServerError()
                .body(BaseResponse.fail(e.getMessage()));
    }
}
