package com.idus.hw.integration.core.user.application;

import com.idus.hw.integration.IntegrationTest;
import com.idus.hw.core.user.application.user.UserSearchPaginationQueryService;
import com.idus.hw.core.user.domain.user.UserRepository;
import com.idus.hw.core.user.domain.user.entity.User;
import com.idus.hw.utils.user.UserTestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UserSearchPaginationQueryService 클래스")
class UserSearchPaginationQueryServiceTest extends IntegrationTest {
    @Autowired
    UserSearchPaginationQueryService userSearchPaginationQueryService;

    @Autowired
    UserRepository userRepository;

    private User user(String email) {
        return UserTestHelper.userBuilder()
                .email(email)
                .build();
    }

    private User user(String email, String name) {
        return UserTestHelper.userBuilder()
                .email(email)
                .name(name)
                .build();
    }

    @DisplayName("searchUsers 메서드는")
    @Nested
    class searchUser {

        @DisplayName("검색어가 없을 때")
        @Nested
        class whenNoKeyword {

            @DisplayName("회원 ID 역순으로 회원 목록을 가져온다")
            @Test
            void aa() {
                // given
                var user1 = userRepository.save(user("tester1@gmail.com"));
                var user2 = userRepository.save(user("tester2@gmail.com"));
                var user3 = userRepository.save(user("tester3@gmail.com"));

                // when
                var page1 = userSearchPaginationQueryService.searchUsers(1, 2);
                var page2 = userSearchPaginationQueryService.searchUsers(2, 2);
                var page3 = userSearchPaginationQueryService.searchUsers(3, 2);

                // then
                assertThat(page1.getContents()).hasSize(2);
                assertThat(page2.getContents()).hasSize(1);
                assertThat(page3.getContents()).isEmpty();

                // ID 역순으로 조회
                assertThat(page1.getContents().get(0).getEmail()).isEqualTo("tester3@gmail.com");
                assertThat(page1.getContents().get(1).getEmail()).isEqualTo("tester2@gmail.com");
                assertThat(page2.getContents().get(0).getEmail()).isEqualTo("tester1@gmail.com");
            }
        }
    }

    @DisplayName("이메일로 검색할 때")
    @Nested
    class whenEmailKeyword {

        @DisplayName("해당 이메일 조건에 맞는 회원 목록을 가져온다")
        @Test
        void aa() {
            // given
            var user1 = userRepository.save(user("ABBB@gmail.com"));
            var user2 = userRepository.save(user("AABB@gmail.com"));
            var user3 = userRepository.save(user("AAAB@gmail.com"));
            var user4 = userRepository.save(user("AAAA@gmail.com"));

            // when
            var page1 = userSearchPaginationQueryService.searchUsers(null, "AA", 1, 2);
            var page2 = userSearchPaginationQueryService.searchUsers(null, "AA", 2, 2);
            var page3 = userSearchPaginationQueryService.searchUsers(null, "AA", 3, 2);

            // then
            assertThat(page1.getContents()).hasSize(2);
            assertThat(page2.getContents()).hasSize(1);
            assertThat(page3.getContents()).isEmpty();

            // 이메일이 `AA`로 시작하는 User 조회
            var searchedUsers = new ArrayList<User>();
            searchedUsers.addAll(page1.getContents());
            searchedUsers.addAll(page2.getContents());
            assertThat(searchedUsers).extracting(User::getEmail).containsExactlyInAnyOrder("AABB@gmail.com", "AAAB@gmail.com", "AAAA@gmail.com");
        }
    }

    @DisplayName("이름으로 검색할 때")
    @Nested
    class whenNameKeyword {

        @DisplayName("해당 이름 조건에 맞는 회원 목록을 가져온다")
        @Test
        void aa() {
            // given
            var user1 = userRepository.save(user("tester1@gmail.com", "ABBB"));
            var user2 = userRepository.save(user("tester2@gmail.com", "AABB"));
            var user3 = userRepository.save(user("tester3@gmail.com", "AAAB"));
            var user4 = userRepository.save(user("tester4@gmail.com", "AAAA"));

            // when
            var page1 = userSearchPaginationQueryService.searchUsers("AA", null, 1, 2);
            var page2 = userSearchPaginationQueryService.searchUsers("AA", null, 2, 2);
            var page3 = userSearchPaginationQueryService.searchUsers("AA", null, 3, 2);

            // then
            assertThat(page1.getContents()).hasSize(2);
            assertThat(page2.getContents()).hasSize(1);
            assertThat(page3.getContents()).isEmpty();

            // 이름이 `AA`로 시작하는 User 조회
            var searchedUsers = new ArrayList<User>();
            searchedUsers.addAll(page1.getContents());
            searchedUsers.addAll(page2.getContents());
            assertThat(searchedUsers).extracting(User::getName).containsExactlyInAnyOrder("AABB", "AAAB", "AAAA");
        }
    }
}
