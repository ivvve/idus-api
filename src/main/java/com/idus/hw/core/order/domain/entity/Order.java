package com.idus.hw.core.order.domain.entity;

import com.idus.hw.common.jpa.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "paid_at")
    private Instant paidAt;

    @Builder
    private Order(Long userId, String orderNumber,
                  String productName, Instant paidAt) {
        // skip validation on purpose
        this.userId = userId;
        this.orderNumber = orderNumber;
        this.productName = productName;
        this.paidAt = paidAt;
    }
}
