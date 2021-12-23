package com.idus.hw.config.jpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.util.Map;

import static com.idus.hw.config.jpa.DataSourceConstants.READ_ONLY_DATA_SOURCE_NAME;
import static com.idus.hw.config.jpa.DataSourceConstants.WRITE_DATA_SOURCE_NAME;

@Slf4j
public class WriteReadOnlyRoutingDataSource extends AbstractRoutingDataSource {
    private final DataSource writeDataSource;
    private final DataSource readOnlyDataSource;

    public WriteReadOnlyRoutingDataSource(
            @Qualifier(WRITE_DATA_SOURCE_NAME) DataSource writeDataSource,
            @Qualifier(READ_ONLY_DATA_SOURCE_NAME) DataSource readOnlyDataSource
    ) {
        this.writeDataSource = writeDataSource;
        this.readOnlyDataSource = readOnlyDataSource;

        super.setTargetDataSources(Map.of(
                RoutingType.WRITE, writeDataSource,
                RoutingType.READ_ONLY, readOnlyDataSource
        ));
    }

    @Override
    protected Object determineCurrentLookupKey() {
        var routingType = TransactionSynchronizationManager.isCurrentTransactionReadOnly() ?
                RoutingType.READ_ONLY : RoutingType.WRITE;

        log.debug("DataSource is routed to {}", routingType);

        return routingType;
    }

    private enum RoutingType {
        WRITE,
        READ_ONLY
    }
}
