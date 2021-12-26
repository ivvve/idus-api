package com.idus.hw.utils.user;

import com.idus.hw.core.user.domain.user.entity.Gender;
import com.idus.hw.core.user.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.test.util.ReflectionTestUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserTestUtils {
    public static User.UserBuilder userBuilder() {
        return User.builder()
                .email("tester@gmail.com")
                .encodedPassword("$argon2i$v=19$m=16,t=2,p=1$YXNkc3ZzYWVy$k0VGBYd0BQywavDv/Cve5A")
                .name("가나다")
                .nickname("nickname")
                .phoneNumber("01011112222")
                .gender(Gender.MALE);
    }

    public static void setId(User user, long id) {
        ReflectionTestUtils.setField(user, "id", id);
    }
}
