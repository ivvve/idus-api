package com.idus.hw.ui.web.user.dto;

import com.idus.hw.common.validation.annotation.*;
import com.idus.hw.core.user.domain.user.entity.Gender;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserJoinRequest {
    @NotBlank
    @EmailFormat
    private String email;

    @NotBlank
    @UserPasswordFormat
    private String password;

    @NotBlank
    @UserNameFormat
    private String name;

    @NotBlank
    @UserNicknameFormat
    private String nickname;

    @NotBlank
    @PhoneNumberFormat
    private String phoneNumber;

    @EnumFormat(nullable = true, enumClass = Gender.class)
    private String gender;

    public Gender getGenderEnumOrNull() {
        if (gender == null) {
            return null;
        }

        return Gender.valueOf(this.gender);
    }
}
