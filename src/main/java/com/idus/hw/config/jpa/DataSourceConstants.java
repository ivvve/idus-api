package com.idus.hw.config.jpa;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataSourceConstants {
    static final String WRITE_DATA_SOURCE_NAME = "writeDataSource";
    static final String READ_ONLY_DATA_SOURCE_NAME = "readOnlyDataSource";
    static final String ROUTING_DATA_SOURCE_NAME = "routingDataSource";
    static final String MAIN_DATA_SOURCE_NAME = "dataSource";
}
