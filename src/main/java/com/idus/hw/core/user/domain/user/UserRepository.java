package com.idus.hw.core.user.domain.user;

import com.idus.hw.core.user.domain.user.entity.User;

import java.util.Optional;

/**
 * @see com.idus.hw.core.user.domain.user.exceptions.UserRepositoryIntegrationException
 */
public interface UserRepository {
    Optional<User> findByEmail(String email);
}
