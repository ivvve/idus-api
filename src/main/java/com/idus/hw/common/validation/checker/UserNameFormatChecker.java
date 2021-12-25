package com.idus.hw.common.validation.checker;

import com.idus.hw.common.utils.KoreanUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserNameFormatChecker {
    private static final int USER_NAME_MAX_LENGTH = 20;

    public static boolean check(String mayUserName) {
        if (StringUtils.isBlank(mayUserName)) {
            return false;
        }

        return StringUtils.isNotBlank(mayUserName) &&
                mayUserName.length() <= USER_NAME_MAX_LENGTH &&
                containsOnlySupportedCharacter(mayUserName);
    }

    private static boolean containsOnlySupportedCharacter(String mayUserName) {
        for (char c : mayUserName.toCharArray()) {
            if (!isSupportedCharacter(c)) {
                return false;
            }
        }

        return true;
    }

    private static boolean isSupportedCharacter(char c) {
        return Character.isLowerCase(c) ||
                Character.isUpperCase(c) ||
                KoreanUtils.isKorean(c);
    }
}
