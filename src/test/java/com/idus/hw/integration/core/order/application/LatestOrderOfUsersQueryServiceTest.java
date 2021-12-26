package com.idus.hw.integration.core.order.application;

import com.idus.hw.IntegrationTest;
import com.idus.hw.core.order.domain.application.LatestOrderOfUsersQueryService;
import com.idus.hw.core.order.domain.entity.Order;
import com.idus.hw.core.order.domain.infrastructure.JpaOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("LatestOrderOfUsersQueryService 클래스")
class LatestOrderOfUsersQueryServiceTest extends IntegrationTest {
    @Autowired
    LatestOrderOfUsersQueryService latestOrderOfUsersQueryService;

    @Autowired
    JpaOrderRepository jpaOrderRepository;

    @DisplayName("getLatestOrderOfUsers 메서드는")
    @Nested
    class getLatestOrderOfUsers {

        @DisplayName("유저의 마지막 주문을 조회한다")
        @Test
        void getLatestOrderOfUsers() {
            // given
            /**
             * Given Order Data
             * /---------/--------------/-----------/
             * / user_id / order_number / paid_at   /
             * /---------/--------------/-----------/
             * / 1       / order_1      / 4 min ago /
             * / 1       / order_2      / 3 min ago /
             * / 2       / order_3      / 2 min ago /
             * / 3       / order_4      / 1 min ago /
             * /---------/--------------/-----------/
             */
            var order1 = this.order1();
            var order2 = this.order2(); // <= user 1의 마지막 주문
            var order3 = this.order3(); // <= user 2의 마지막 주문
            var order4 = this.order4(); // <= user 3의 마지막 주문
            jpaOrderRepository.saveAll(List.of(order1, order2, order3, order4));

            // when
            var latestOrderOfUsers =
                    latestOrderOfUsersQueryService.getLatestOrderOfUsers(List.of(1L, 2L));

            // then
            assertThat(latestOrderOfUsers).hasSize(2);
            assertThat(this.getOrderOfUser(1L, latestOrderOfUsers))
                    .isPresent()
                    .map(Order::getId).hasValue(order2.getId());
            assertThat(this.getOrderOfUser(2L, latestOrderOfUsers))
                    .isPresent()
                    .map(Order::getId).hasValue(order3.getId());
        }

        private Order order1() {
            return Order.builder()
                    .userId(1L)
                    .orderNumber("order_000001")
                    .productName("product_1")
                    .paidAt(Instant.now().minus(4, ChronoUnit.MINUTES))
                    .build();
        }

        private Order order2() {
            return Order.builder()
                    .userId(1L)
                    .orderNumber("order_000002")
                    .productName("product_2")
                    .paidAt(Instant.now().minus(3, ChronoUnit.MINUTES))
                    .build();
        }

        private Order order3() {
            return Order.builder()
                    .userId(2L)
                    .orderNumber("order_000003")
                    .productName("product_3")
                    .paidAt(Instant.now().minus(2, ChronoUnit.MINUTES))
                    .build();
        }

        private Order order4() {
            return Order.builder()
                    .userId(3L)
                    .orderNumber("order_000004")
                    .productName("product_4")
                    .paidAt(Instant.now().minus(1, ChronoUnit.MINUTES))
                    .build();
        }

        private Optional<Order> getOrderOfUser(long userId, List<Order> Orders) {
            return Orders.stream()
                    .filter(it -> Objects.equals(it.getUserId(), userId))
                    .findFirst();
        }
    }
}
