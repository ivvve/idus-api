package com.idus.hw.core.order.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LatestUserOrderInfo {
    private Long orderId;
    private Long userId;
}
