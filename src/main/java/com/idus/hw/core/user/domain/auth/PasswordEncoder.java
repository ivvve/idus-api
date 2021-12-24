package com.idus.hw.core.user.domain.auth;

import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {
    // TODO change to use Hashing algorithm
    public boolean matches(String rawPassword, String encodedPassword) {
        return rawPassword.equals(encodedPassword);
    }
}
