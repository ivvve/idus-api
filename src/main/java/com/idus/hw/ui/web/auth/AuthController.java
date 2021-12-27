package com.idus.hw.ui.web.auth;

import com.idus.hw.common.web.WebConstants;
import com.idus.hw.common.web.dto.BaseResponse;
import com.idus.hw.ui.web.auth.dto.LoginRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @PostMapping(WebConstants.URL.LOGIN_REQUEST_PATH)
    @ApiOperation("Login API")
    public ResponseEntity<BaseResponse<Void>> login(@RequestBody LoginRequest request) {
        // Servlet Filter를 통해 Login API 처리가 된다.
        return ResponseEntity.ok(BaseResponse.success());
    }

    @PostMapping(WebConstants.URL.LOGOUT_REQUEST_PATH)
    @ApiOperation("Logout API")
    public ResponseEntity<BaseResponse<Void>> logout() {
        // Servlet Filter를 통해 Login API 처리가 된다.
        return ResponseEntity.ok(BaseResponse.success());
    }
}
