package com.idus.hw.integration.core.order.application;

import com.idus.hw.integration.IntegrationTest;
import com.idus.hw.core.order.domain.application.OrdersOfUserQueryService;
import com.idus.hw.core.order.domain.entity.Order;
import com.idus.hw.core.order.domain.infrastructure.JpaOrderRepository;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("OrdersOfUserQueryService")
public class OrdersOfUserQueryServiceTest extends IntegrationTest {
    @Autowired
    OrdersOfUserQueryService ordersOfUserQueryService;

    @Autowired
    JpaOrderRepository orderRepository;

    @DisplayName("getOrdersOfUserByLatestOrder 메서드는")
    @Nested
    class getOrdersOfUserByLatestOrder {

        @DisplayName("회원의 주문 목록을 페이지네이션으로 조회한다")
        @Test
        void getOrdersOfUserUsingPagination() {
            // given
            /**
             * Given Order Data
             * /---------/--------------/-----------/
             * / user_id / order_number / paid_at   /
             * /---------/--------------/-----------/
             * / 1       / order_1      / 10 min ago /
             * / 2       / order_2      / 9 min ago /
             * / 1       / order_3      / 8 min ago /
             * / 2       / order_4      / 7 min ago /
             * / 1       / order_5      / 6 min ago /
             * / 2       / order_6      / 5 min ago /
             * / 1       / order_7      / 4 min ago /
             * / 2       / order_8      / 3 min ago /
             * / 1       / order_9      / 2 min ago /
             * / 2       / order_10     / 1 min ago /
             * /---------/--------------/-----------/
             */
            for (int i = 1; i <= 10; i += 2) {
                orderRepository.save(order(1L, i - 1));
                orderRepository.save(order(2L, i));
            }

            // when
            var user1Orders1 = ordersOfUserQueryService.getOrdersOfUserByLatestOrder(1L, 2);
            var user1Orders2 = ordersOfUserQueryService.getOrdersOfUserByLatestOrder(1L, user1Orders1.get(1).getId(), 2);
            var user1Orders3 = ordersOfUserQueryService.getOrdersOfUserByLatestOrder(1L, user1Orders2.get(1).getId(), 2);
            var user1Orders4 = ordersOfUserQueryService.getOrdersOfUserByLatestOrder(1L, user1Orders3.get(0).getId(), 2);

            // then
            // 조회 개수 확인
            assertThat(user1Orders1).hasSize(2);
            assertThat(user1Orders2).hasSize(2);
            assertThat(user1Orders3).hasSize(1);
            assertThat(user1Orders4).isEmpty();

            // 최신순 정렬 확인
            var user1Orders = this.mergeOrders(user1Orders1, user1Orders2, user1Orders3);
            this.assertThatOrdersIsLatestOrder(user1Orders);
        }

        private Order order(long userId, int increasingInt) {
            var orderNumber = "order_" + StringUtils.leftPad("" + increasingInt, 6, '0');

            return Order.builder()
                    .userId(userId)
                    .orderNumber(orderNumber)
                    .productName("product" + increasingInt)
                    .paidAt(Instant.now().minus(10 - increasingInt, ChronoUnit.MINUTES))
                    .build();
        }

        private List<Order> mergeOrders(List<Order>... orders) {
            return Arrays.stream(orders)
                    .flatMap(it -> it.stream())
                    .collect(Collectors.toList());
        }

        private void assertThatOrdersIsLatestOrder(List<Order> orders) {
            long nextId = Long.MAX_VALUE;

            for (Order order : orders) {
                if (nextId < order.getId()) {
                    Assertions.fail("List<Order> is not ordered by latest");
                    return;
                }

                nextId = order.getId();
            }
        }
    }
}
