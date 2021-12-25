package com.idus.hw.ui.web.user.dto;

import com.idus.hw.core.user.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class UserJoinResponse {
    private long id;
    private String email;
    private String name;
    private String nickname;
    private String gender;

    public static UserJoinResponse of(User user) {
        return UserJoinResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .nickname(user.getNickname())
                .gender(user.getGender().name())
                .build();
    }
}
