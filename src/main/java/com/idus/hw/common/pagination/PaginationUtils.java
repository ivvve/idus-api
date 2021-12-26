package com.idus.hw.common.pagination;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaginationUtils {
    public static int calculateTotalPage(long totalCount, int pageSize) {
        return (int) Math.ceil((totalCount * 1.0) / pageSize);
    }

    public static int calculateOffset(int page, int pageSize) {
        return (page - 1) * pageSize;
    }
}
