package com.idus.hw.core.order.domain.application;

import com.idus.hw.core.order.domain.application.dto.LatestUserOrderInfo;
import com.idus.hw.core.order.domain.entity.Order;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.idus.hw.core.order.domain.entity.QOrder.order;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LatestOrderOfUsersQueryService {
    private final JPAQueryFactory jpaQueryFactory;

    public List<Order> getLatestOrderOfUsers(List<Long> userIds) {
        if (userIds.isEmpty()) {
            return Collections.emptyList();
        }

        var latestOrderOfUsers = this.getLatestOrderInfoOfUsers(userIds);
        var latestOrderIdOfUsers = this.extractOrderIdFrom(latestOrderOfUsers);
        return this.getOrders(latestOrderIdOfUsers);
    }

    private List<LatestUserOrderInfo> getLatestOrderInfoOfUsers(List<Long> userIds) {
        return this.jpaQueryFactory
                .select(Projections.constructor(LatestUserOrderInfo.class,
                        order.id.max().as("id"),
                        order.userId
                ))
                .from(order)
                .where(order.userId.in(userIds))
                .groupBy(order.userId)
                .fetch();
    }

    private List<Long> extractOrderIdFrom(List<LatestUserOrderInfo> latestOrderOfUsers) {
        return latestOrderOfUsers.stream()
                .map(LatestUserOrderInfo::getOrderId)
                .collect(Collectors.toList());
    }

    private List<Order> getOrders(List<Long> orderIds) {
        return this.jpaQueryFactory
                .selectFrom(order)
                .from(order)
                .where(order.id.in(orderIds))
                .fetch();
    }
}
