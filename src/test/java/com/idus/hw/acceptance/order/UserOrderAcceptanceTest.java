package com.idus.hw.acceptance.order;

import com.idus.hw.acceptance.AcceptanceTest;
import com.idus.hw.core.order.domain.entity.Order;
import com.idus.hw.core.order.infrastructure.JpaOrderRepository;
import com.idus.hw.core.user.infrastructure.user.JpaUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

import static com.idus.hw.acceptance.auth.steps.AuthAcceptanceTestSteps.회원_가입_후_로그인을_했다;
import static com.idus.hw.acceptance.order.steps.OrderAcceptanceTestSteps.*;

@DisplayName("회원 주문 조회 Acceptance Test")
public class UserOrderAcceptanceTest extends AcceptanceTest {

    @Autowired
    JpaUserRepository userRepository;
    @Autowired
    JpaOrderRepository orderRepository;

    @DisplayName("회원 주문 조회 기능")
    @Test
    void userOrders() {
        // given
        var 회원_ID = 회원_가입_후_로그인을_했다("tester@gmail.com", "Passw@rd123");
        for (int i = 1; i <= 3; i++) {
            this.주문을_했다(회원_ID, i);
        }

        // when
        var 회원의_주문_목록_조회_응답 = 회원의_주문_목록_조회를_요청한다(회원_ID, 2);
        // then
        회원의_주문_목록_조회가_성공한다(회원의_주문_목록_조회_응답);

        // when
        var 주문_ID = 회원의_주문_목록의_마지막_주문_ID를_가져온다(회원의_주문_목록_조회_응답);
        회원의_주문_목록_조회_응답 = 회원의_다음_주문_목록_조회를_요청한다(회원_ID, 주문_ID, 2);
        // then
        회원의_주문_목록_조회가_성공한다(회원의_주문_목록_조회_응답);
    }

    private void 주문을_했다(long userId, int i) {
        var order = Order.builder()
                .userId(userId)
                .orderNumber("order" + i)
                .productName("product" + i)
                .paidAt(Instant.now())
                .build();

        this.orderRepository.save(order);
    }
}
