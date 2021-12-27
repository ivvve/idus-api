package com.idus.hw.ui.web.order;

import com.idus.hw.common.web.dto.BaseResponse;
import com.idus.hw.core.order.domain.application.OrdersOfUserQueryService;
import com.idus.hw.core.order.domain.entity.Order;
import com.idus.hw.ui.web.order.dto.UserOrdersRequest;
import com.idus.hw.ui.web.order.dto.UserOrdersResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/orders/users")
@RequiredArgsConstructor
public class UserOrderController {
    private final OrdersOfUserQueryService ordersOfUserQueryService;

    @ApiOperation("회원 주문 조회")
    @GetMapping("/{userId}")
    public ResponseEntity<BaseResponse<UserOrdersResponse>> getUserOrders(
            ZoneId zoneId,
            @PathVariable Long userId,
            UserOrdersRequest request
    ) {
        var userOrders = this.getUserOrders(userId, request);
        return ResponseEntity.ok(
                BaseResponse.success(UserOrdersResponse.of(userOrders, zoneId))
        );
    }

    private List<Order> getUserOrders(Long userId, UserOrdersRequest request) {
        if (request.isContinuousRequest()) {
            return this.ordersOfUserQueryService
                    .getOrdersOfUserByLatestOrder(userId, request.getLastReadOrderId(), request.getPageSize());
        }

        return this.ordersOfUserQueryService.getOrdersOfUserByLatestOrder(userId, request.getPageSize());
    }
}
