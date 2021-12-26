package com.idus.hw.mock.core.user.domain.user;

import com.idus.hw.core.user.domain.user.UserRepository;
import com.idus.hw.core.user.domain.user.entity.User;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MockUserRepository implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private long id = 1;

    @Override
    public User save(User user) {
        ReflectionTestUtils.setField(user, "id", this.id);
        this.id++;

        this.users.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.users.values().stream()
                .filter(it -> it.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public boolean existsByEmail(String email) {
        return this.findByEmail(email).isPresent();
    }

    @Override
    public Optional<User> findById(long userId) {
        return Optional.ofNullable(this.users.get(userId));
    }

    public void reset() {
        this.users.clear();
    }
}
