package com.idus.hw.ui.web.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.idus.hw.core.user.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class UserDetailsResponse {
    @JsonProperty("id")
    private long id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("gender")
    private String gender;

    public static UserDetailsResponse of(User user) {
        var gender = user.getGender();

        return UserDetailsResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .nickname(user.getNickname())
                .gender(Objects.isNull(gender) ? null : gender.name())
                .build();
    }
}
