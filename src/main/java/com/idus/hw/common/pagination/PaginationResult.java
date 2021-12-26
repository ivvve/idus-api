package com.idus.hw.common.pagination;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class PaginationResult<T> {
    private final long totalCount;
    private final int pageSize;

    private final int totalPage;
    private final int currentPage;

    private final List<T> contents;

    @Builder(access = AccessLevel.PRIVATE)
    private PaginationResult(long totalCount, int pageSize,
                             int currentPage, List<T> contents) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.totalPage = PaginationUtils.calculateTotalPage(totalCount, pageSize);
        this.currentPage = currentPage;
        this.contents = contents;
    }

    public static PaginationResult empty(int pageSize, int currentPage) {
        return PaginationResult.builder()
                .totalCount(0)
                .pageSize(pageSize)
                .currentPage(currentPage)
                .contents(Collections.emptyList())
                .build();
    }

    public static PaginationResult noContents(long totalCount, int pageSize,
                                              int currentPage) {
        return PaginationResult.builder()
                .totalCount(totalCount)
                .pageSize(pageSize)
                .currentPage(currentPage)
                .contents(Collections.emptyList())
                .build();
    }

    public static PaginationResult withContents(long totalCount, int pageSize,
                                                int currentPage, List contents) {
        return PaginationResult.builder()
                .totalCount(totalCount)
                .pageSize(pageSize)
                .currentPage(currentPage)
                .contents(contents)
                .build();
    }
}
