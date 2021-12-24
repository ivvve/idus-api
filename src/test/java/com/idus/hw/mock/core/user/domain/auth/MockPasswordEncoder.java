package com.idus.hw.mock.core.user.domain.auth;

import com.idus.hw.core.user.domain.auth.PasswordEncoder;

public class MockPasswordEncoder extends PasswordEncoder {
    private boolean matchResult = true;

    public void fixMatchResult(boolean matchResult) {
        this.matchResult = matchResult;
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return this.matchResult;
    }
}
