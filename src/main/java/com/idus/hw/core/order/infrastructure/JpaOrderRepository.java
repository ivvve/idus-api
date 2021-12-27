package com.idus.hw.core.order.infrastructure;

import com.idus.hw.core.order.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends JpaRepository<Order, Long> {
}
