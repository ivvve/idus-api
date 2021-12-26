package com.idus.hw.core.user.application.user;

import com.idus.hw.core.user.domain.user.UserRepository;
import com.idus.hw.core.user.domain.user.entity.User;
import com.idus.hw.core.user.domain.user.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserDetailsQueryService {
    private final UserRepository userRepository;

    public User getUser(long userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
