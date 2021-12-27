package com.idus.hw.ui.web.user;

import com.idus.hw.common.web.dto.BaseResponse;
import com.idus.hw.core.user.application.user.UserDetailsQueryService;
import com.idus.hw.core.user.application.user.UserJoinService;
import com.idus.hw.ui.web.user.dto.UserDetailsResponse;
import com.idus.hw.ui.web.user.dto.UserJoinRequest;
import com.idus.hw.ui.web.user.dto.UserJoinResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserJoinService userJoinService;
    private final UserDetailsQueryService userDetailsQueryService;

    @ApiOperation("회원 가입 API")
    @PostMapping
    public ResponseEntity<BaseResponse<UserJoinResponse>> joinUser(
            @Valid @RequestBody UserJoinRequest request
    ) {
        var joinedUser = this.userJoinService.join(
                request.getEmail(),
                request.getPassword(),
                request.getName(),
                request.getNickname(),
                request.getPhoneNumber(),
                request.getGenderEnumOrNull()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(
                        UserJoinResponse.of(joinedUser)
                ));
    }

    @ApiOperation("회원 조회 API")
    @GetMapping("/{userId}")
    public ResponseEntity<BaseResponse<UserDetailsResponse>> getUserDetails(
            @PathVariable long userId
    ) {
        var user = this.userDetailsQueryService.getUser(userId);
        return ResponseEntity.ok(BaseResponse.success(
                UserDetailsResponse.of(user)
        ));
    }
}
