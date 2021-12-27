package com.idus.hw.ui.web.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.idus.hw.common.collection.CollectionConverter;
import com.idus.hw.common.time.TimeFormattingUtils;
import com.idus.hw.core.order.domain.entity.Order;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.ZoneId;
import java.util.List;

@Data
@AllArgsConstructor
public class UserOrdersResponse {
    @JsonProperty("content")
    private List<UserOrder> contents;

    public static UserOrdersResponse of(List<Order> userOrders,
                                        ZoneId zoneId) {
        return new UserOrdersResponse(UserOrder.of(userOrders, zoneId));
    }

    @Data
    @Builder(access = AccessLevel.PRIVATE)
    public static class UserOrder {
        private long id;
        private String orderNumber;
        private String productName;
        private String paidAt;

        private static List<UserOrder> of(List<Order> orders, ZoneId zoneId) {
            return CollectionConverter.convertAndToList(orders, it -> of(it, zoneId));
        }

        private static UserOrder of(Order order, ZoneId zoneId) {
            var formattedPaidAt =
                    TimeFormattingUtils.toZonedTimeStampFormat(order.getPaidAt(), zoneId);

            return UserOrder.builder()
                    .id(order.getId())
                    .orderNumber(order.getOrderNumber())
                    .productName(order.getProductName())
                    .paidAt(formattedPaidAt)
                    .build();
        }
    }
}
