package com.idus.hw.ui.web.auth.dto;

import com.idus.hw.common.validation.annotation.EmailFormat;
import com.idus.hw.common.validation.annotation.UserPasswordFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {
    @NotNull
    @EmailFormat
    private String email;

    @NotNull
    @UserPasswordFormat
    private String password;
}
