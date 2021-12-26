package com.idus.hw.unit.common.collection;

import com.idus.hw.common.collection.CollectionConverter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CollectionConverter 클래스")
class CollectionConverterTest {

    @DisplayName("toMap 메서드는")
    @Nested
    class toMap {
        @DisplayName("Collection을 Map 형태로 변환시킨다")
        @Test
        void convertCollectionIntoMap() {
            // given
            var dto1 = new TestDto("k1", "v1");
            var dto2 = new TestDto("k2", "v2");
            var dto3 = new TestDto("k3", "v3");

            // when
            var testDtoMap = CollectionConverter.toMap(List.of(dto1, dto2, dto3), TestDto::getKey);

            // then
            assertThat(testDtoMap.get("k1")).isSameAs(dto1);
            assertThat(testDtoMap.get("k2")).isSameAs(dto2);
            assertThat(testDtoMap.get("k3")).isSameAs(dto3);
        }
    }

    @Getter
    @RequiredArgsConstructor
    class TestDto {
        private final String key;
        private final String value;
    }
}


