package com.idus.hw.ui.web.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.idus.hw.common.collection.CollectionConverter;
import com.idus.hw.common.pagination.PaginationResult;
import com.idus.hw.common.time.TimeFormattingUtils;
import com.idus.hw.core.order.domain.entity.Order;
import com.idus.hw.core.user.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class SearchUsersResponse {
    @JsonProperty("totalCount")
    private long totalCount;

    @JsonProperty("pageSize")
    private int pageSize;

    @JsonProperty("totalPage")
    private int totalPage;

    @JsonProperty("currentPage")
    private int currentPage;

    @JsonProperty("content")
    private List<UserAndLatestOrder> contents;

    public static SearchUsersResponse of(
            PaginationResult<User> userPaginationResult, List<Order> latestOrderOfUsers, ZoneId zoneId
    ) {
        var contents = toUserAndLatestOrders(userPaginationResult.getContents(), latestOrderOfUsers, zoneId);

        return SearchUsersResponse.builder()
                .totalCount(userPaginationResult.getTotalCount())
                .pageSize(userPaginationResult.getPageSize())
                .totalPage(userPaginationResult.getTotalPage())
                .currentPage(userPaginationResult.getCurrentPage())
                .contents(contents)
                .build();
    }

    private static List<UserAndLatestOrder> toUserAndLatestOrders(
            List<User> users, List<Order> latestOrderOfUsers, ZoneId zoneId
    ) {
        var latestOrderOfUsersMap = CollectionConverter.toMap(latestOrderOfUsers, Order::getUserId);
        return users.stream()
                .map(it -> UserAndLatestOrder.of(
                                it,
                                latestOrderOfUsersMap.get(it.getId()),
                                zoneId
                        )
                )
                .collect(Collectors.toList());
    }

    @Data
    @Builder(access = AccessLevel.PRIVATE)
    public static class UserAndLatestOrder {
        // User Info
        @JsonProperty("email")
        private final String email;

        @JsonProperty("name")
        private final String name;

        @JsonProperty("nickname")
        private final String nickname;

        @JsonProperty("gender")
        private final String gender;

        // Order Info
        @JsonProperty("orderNumber")
        private final String orderNumber;

        @JsonProperty("productName")
        private final String productName;

        @JsonProperty("paidAt")
        private final String paidAt;

        private static UserAndLatestOrder of(User user,
                                             Order userLatestOrder,
                                             ZoneId zoneId) {
            var hasGender = (user.getGender() != null);
            var hasLatestOrder = (userLatestOrder != null);
            var formattedPaidAt = hasLatestOrder ?
                    TimeFormattingUtils.toZonedTimeStampFormat(userLatestOrder.getPaidAt(), zoneId) : null;

            return UserAndLatestOrder.builder()
                    .email(user.getName())
                    .name(user.getName())
                    .nickname(user.getNickname())
                    .gender(hasGender ? user.getGender().name() : null)
                    .orderNumber(hasLatestOrder ? userLatestOrder.getOrderNumber() : null)
                    .productName(hasLatestOrder ? userLatestOrder.getProductName() : null)
                    .paidAt(formattedPaidAt)
                    .build();
        }
    }
}
