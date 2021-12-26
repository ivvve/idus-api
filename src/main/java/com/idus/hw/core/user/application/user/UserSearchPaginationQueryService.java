package com.idus.hw.core.user.application.user;

import com.idus.hw.common.collection.CollectionConverter;
import com.idus.hw.common.pagination.PaginationResult;
import com.idus.hw.common.pagination.PaginationUtils;
import com.idus.hw.common.querydsl.OrderByNull;
import com.idus.hw.core.user.domain.user.entity.User;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.idus.hw.core.user.domain.user.entity.QUser.user;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserSearchPaginationQueryService {
    private final JPAQueryFactory jpaQueryFactory;

    public PaginationResult<User> searchUsers(int page, int pageSize) {
        return this.searchUsers(Optional.empty(), Optional.empty(), page, pageSize);
    }

    public PaginationResult<User> searchUsers(String name, String email,
                                              int page, int pageSize) {
        return this.searchUsers(Optional.ofNullable(name), Optional.ofNullable(email), page, pageSize);
    }

    private PaginationResult<User> searchUsers(Optional<String> name, Optional<String> email,
                                               int page, int pageSize
    ) {
        var totalCount = this.getTotalCount(name, email);

        if (totalCount == 0) {
            return PaginationResult.empty(pageSize, page);
        }

        var totalPage = PaginationUtils.calculateTotalPage(totalCount, pageSize);

        if (totalPage < page) {
            return PaginationResult.noContents(totalCount, pageSize, page);
        }

        var userIds = this.searchUsersIdsBy(name, email, page, pageSize);
        var users = this.getUsers(userIds);
        return PaginationResult.withContents(totalCount, pageSize, page, users);
    }

    private long getTotalCount(Optional<String> name, Optional<String> email) {
        return this.jpaQueryFactory
                .selectFrom(user)
                .where(this.userNameStartsWith(name),
                        this.userEmailStartsWith(email))
                .fetchCount();
    }

    private List<Long> searchUsersIdsBy(Optional<String> name, Optional<String> email,
                                        int page, int pageSize) {
        return this.jpaQueryFactory
                .select(user.id)
                .from(user)
                .where(this.userNameStartsWith(name),
                        this.userEmailStartsWith(email))
                .orderBy(this.orderByIdDescIfNoKeyword(name, email))
                .offset(PaginationUtils.calculateOffset(page, pageSize))
                .limit(pageSize)
                .fetch();
    }

    private BooleanExpression userNameStartsWith(Optional<String> name) {
        return name
                .map(it -> user.name.startsWith(it))
                .orElse(null);
    }

    private BooleanExpression userEmailStartsWith(Optional<String> email) {
        return email
                .map(it -> user.email.startsWith(it))
                .orElse(null);
    }

    private OrderSpecifier orderByIdDescIfNoKeyword(Optional<String> name, Optional<String> email) {
        return (name.isEmpty() && email.isEmpty()) ? user.id.desc() : OrderByNull.DEFAULT;
    }

    private List<User> getUsers(List<Long> userIds) {
        var users = this.jpaQueryFactory
                .selectFrom(user)
                .where(user.id.in(userIds))
                .fetch();

        return this.sortAccordingToUserId(userIds, users);
    }

    private List<User> sortAccordingToUserId(List<Long> userIds, List<User> users) {
        var userMap = CollectionConverter.toMap(users, User::getId);
        return userIds.stream()
                .map(userMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
