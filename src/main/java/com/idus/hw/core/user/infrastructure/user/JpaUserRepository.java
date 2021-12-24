package com.idus.hw.core.user.infrastructure.user;

import com.idus.hw.core.user.domain.user.UserRepository;
import com.idus.hw.core.user.domain.user.entity.User;
import com.idus.hw.core.user.domain.user.exceptions.UserRepositoryIntegrationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.function.Supplier;

interface InnerUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

@Repository
@RequiredArgsConstructor
public class JpaUserRepository implements UserRepository {
    private final InnerUserRepository repository;

    @Override
    public Optional<User> findByEmail(String email) {
        return this.wrapIntegrationException(() -> {
            return this.repository.findByEmail(email);
        });
    }

    private <T> T wrapIntegrationException(Supplier<T> process) {
        try {
            return process.get();
        } catch (Exception e) {
            throw new UserRepositoryIntegrationException(e);
        }
    }
}

