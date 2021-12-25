package com.idus.hw.core.user.application.user;

import com.idus.hw.core.user.domain.auth.PasswordEncoder;
import com.idus.hw.core.user.domain.user.UserRepository;
import com.idus.hw.core.user.domain.user.entity.Gender;
import com.idus.hw.core.user.domain.user.entity.User;
import com.idus.hw.core.user.domain.user.exceptions.UserDuplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserJoinService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User join(String email, String password, String name, String nickname,
                     String phoneNumber, @Nullable Gender gender) {
        var userWithEmailExists = this.userRepository.existsByEmail(email);

        if (userWithEmailExists) {
            throw UserDuplicationException.duplicatedEmail(email);
        }

        var encodedPassword = passwordEncoder.encode(password);
        var user = User.builder()
                .email(email)
                .encodedPassword(encodedPassword)
                .name(name)
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .gender(gender)
                .build();
        return this.userRepository.save(user);
    }
}
