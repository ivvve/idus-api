package com.idus.hw.ui.web.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.idus.hw.common.validation.annotation.*;
import com.idus.hw.core.user.domain.user.entity.Gender;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserJoinRequest {
    @NotBlank
    @EmailFormat
    @JsonProperty("email")
    private String email;

    @NotBlank
    @UserPasswordFormat
    @JsonProperty("password")
    private String password;

    @NotBlank
    @UserNameFormat
    @JsonProperty("name")
    private String name;

    @NotBlank
    @UserNicknameFormat
    @JsonProperty("nickname")
    private String nickname;

    @NotBlank
    @PhoneNumberFormat
    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @EnumFormat(nullable = true, enumClass = Gender.class)
    @JsonProperty("gender")
    private String gender;

    public Gender getGenderEnumOrNull() {
        if (gender == null) {
            return null;
        }

        return Gender.valueOf(this.gender);
    }
}
