package com.idus.hw.ui.web.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.annotation.Nullable;

@Data
public class UserOrdersRequest {
    private static final int DEFAULT_PAGE_SIZE = 30;

    @JsonProperty("lastReadOrderId")
    @Nullable
    private Long lastReadOrderId;

    @JsonProperty("pageSize")
    @Nullable
    private Integer pageSize;

    @Nullable
    public Integer getPageSize() {
        return (this.pageSize == null) ? DEFAULT_PAGE_SIZE : this.pageSize;
    }

    public boolean isContinuousRequest() {
        return this.lastReadOrderId != null;
    }
}
