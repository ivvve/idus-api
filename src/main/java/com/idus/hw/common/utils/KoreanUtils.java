package com.idus.hw.common.utils;

import java.util.regex.Pattern;

public class KoreanUtils {
    private static final String KOREAN_REGEX = "^[가-힣]*$";
    private static final Pattern KOREAN_PATTERN = Pattern.compile(KOREAN_REGEX);

    public static boolean isKorean(String str) {
        if (str.isEmpty()) {
            return false;
        }

        return KOREAN_PATTERN.matcher(str).matches();
    }

    public static boolean isKorean(char c) {
        return KOREAN_PATTERN.matcher(String.valueOf(c)).matches();
    }
}
