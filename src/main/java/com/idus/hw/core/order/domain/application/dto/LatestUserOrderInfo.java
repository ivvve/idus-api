package com.idus.hw.core.order.domain.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LatestUserOrderInfo {
    private Long orderId;
    private Long userId;
}
