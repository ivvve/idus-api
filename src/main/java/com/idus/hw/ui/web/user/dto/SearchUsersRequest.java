package com.idus.hw.ui.web.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;

@Data
public class SearchUsersRequest {
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 20;

    @Nullable
    @JsonProperty("name")
    private String name;

    @Nullable
    @JsonProperty("email")
    private String email;

    @Nullable
    @JsonProperty("page")
    private Integer page;

    @Nullable
    @JsonProperty("pageSize")
    private Integer pageSize;

    public boolean hasKeyword() {
        return StringUtils.isNotBlank(this.name) ||
                StringUtils.isNotBlank(this.email);
    }

    public Integer getPage() {
        return (this.page == null) ? DEFAULT_PAGE : this.page;
    }

    @Nullable
    public Integer getPageSize() {
        return (this.pageSize == null) ? DEFAULT_PAGE_SIZE : this.pageSize;
    }
}
