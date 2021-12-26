package com.idus.hw.core.order.domain.application;

import com.idus.hw.core.order.domain.entity.Order;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.idus.hw.core.order.domain.entity.QOrder.order;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrdersOfUserQueryService {
    private final JPAQueryFactory jpaQueryFactory;

    public List<Order> getOrdersOfUserByLatestOrder(
            long userId, int pageSize
    ) {
        return this.getOrdersOfUserByLatestOrder(
                userId, Optional.empty(), pageSize
        );
    }

    public List<Order> getOrdersOfUserByLatestOrder(
            long userId, long lastReadOrderId, int pageSize
    ) {
        return this.getOrdersOfUserByLatestOrder(
                userId, Optional.of(lastReadOrderId), pageSize
        );
    }

    private List<Order> getOrdersOfUserByLatestOrder(
            long userId, Optional<Long> lastReadOrderId, int pageSize
    ) {
        return this.jpaQueryFactory
                .selectFrom(order)
                .where(
                        order.userId.eq(userId),
                        this.orderIdLt(lastReadOrderId)
                )
                .orderBy(order.id.desc())
                .limit(pageSize)
                .fetch();
    }

    private BooleanExpression orderIdLt(Optional<Long> latestReadOrderId) {
        return latestReadOrderId
                .map(it -> order.id.lt(it))
                .orElse(null);
    }
}
