package com.idus.hw.common.validation.checker;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserNicknameFormatChecker {
    private static final int USER_NICKNAME_MAX_LENGTH = 30;

    public static boolean check(String mayNickname) {
        if (StringUtils.isBlank(mayNickname)) {
            return false;
        }

        return StringUtils.isNotBlank(mayNickname) &&
                (mayNickname.length() <= USER_NICKNAME_MAX_LENGTH) &&
                StringUtils.isAllLowerCase(mayNickname);
    }
}
