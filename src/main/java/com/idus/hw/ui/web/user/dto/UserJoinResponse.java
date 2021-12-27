package com.idus.hw.ui.web.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.idus.hw.core.user.domain.user.entity.User;
import lombok.*;

@Data
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserJoinResponse {
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

    public static UserJoinResponse of(User user) {
        var gender = user.getGender();
        return UserJoinResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .nickname(user.getNickname())
                .gender((gender == null) ? null : gender.name())
                .build();
    }
}
