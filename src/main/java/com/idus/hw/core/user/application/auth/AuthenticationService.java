package com.idus.hw.core.user.application.auth;

import com.idus.hw.core.user.domain.auth.PasswordEncoder;
import com.idus.hw.core.user.domain.auth.exceptions.AuthenticationFailException;
import com.idus.hw.core.user.domain.user.UserRepository;
import com.idus.hw.core.user.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * @return Authenticated User
     */
    @Transactional
    public User authenticate(String email, String password) {
        var userOptional = this.userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw AuthenticationFailException.userNotRegistered(email);
        }

        var user = userOptional.get();
        var passwordMatched = this.passwordEncoder.matches(password, user.getEncodedPassword());

        if (!passwordMatched) {
            throw AuthenticationFailException.passwordNotMatched(email);
        }

        return user;
    }
}
