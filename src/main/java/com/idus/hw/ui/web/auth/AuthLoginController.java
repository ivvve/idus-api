package com.idus.hw.ui.web.auth;

import com.idus.hw.common.validation.annotation.EmailFormat;
import com.idus.hw.common.validation.annotation.UserPasswordFormat;
import com.idus.hw.common.web.dto.BaseResponse;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
public class AuthLoginController {
    @ApiOperation("Login API")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        // Servlet Filter를 통해 Login API 처리가 된다.
        return null;
    }

    @Data
    public static class LoginRequest {
        @NotNull
        @EmailFormat
        private String email;

        @NotNull
        @UserPasswordFormat
        private String password;
    }

    @Data
    public static class LoginResponse extends BaseResponse {
    }
}
