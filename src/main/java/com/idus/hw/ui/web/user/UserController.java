package com.idus.hw.ui.web.user;

import com.idus.hw.core.user.application.user.UserJoinService;
import com.idus.hw.ui.web.user.dto.UserJoinRequest;
import com.idus.hw.ui.web.user.dto.UserJoinResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserJoinService userJoinService;

    @ApiOperation("회원가입 API")
    @PostMapping
    public ResponseEntity<UserJoinResponse> joinUser(
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
                .body(UserJoinResponse.of(joinedUser));
    }
}
