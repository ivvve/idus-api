package com.idus.hw.unit.common.pagination;

import com.idus.hw.common.pagination.PaginationUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PaginationUtils 클래스")
class PaginationUtilsTest {

    @DisplayName("calculateTotalPage 메서드는")
    @Nested
    class calculateTotalPage {

        @DisplayName("해당 페이지 정보를 받아 전체 페이지 수를 구한다")
        @Test
        void returnsTotalPage() {
            Assertions.assertThat(PaginationUtils.calculateTotalPage(100, 6)).isEqualTo(17);
            assertThat(PaginationUtils.calculateTotalPage(100, 5)).isEqualTo(20);
            assertThat(PaginationUtils.calculateTotalPage(100, 4)).isEqualTo(25);
            assertThat(PaginationUtils.calculateTotalPage(100, 3)).isEqualTo(34);
        }
    }
}