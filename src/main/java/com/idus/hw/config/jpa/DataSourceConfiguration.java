package com.idus.hw.config.jpa;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import static com.idus.hw.config.jpa.DataSourceConstants.*;

@Configuration
@EnableAutoConfiguration(
        exclude = {DataSourceAutoConfiguration.class}
)
@EnableTransactionManagement
public class DataSourceConfiguration {
    @Bean(name = WRITE_DATA_SOURCE_NAME)
    @ConfigurationProperties(prefix = "spring.datasource.write.hikari")
    public DataSource writeDataSource() {
        return new HikariDataSource();
    }

    @Bean(name = READ_ONLY_DATA_SOURCE_NAME)
    @ConfigurationProperties(prefix = "spring.datasource.read-only.hikari")
    public DataSource readOnlyDataSource() {
        return new HikariDataSource();
    }

    @Bean(name = ROUTING_DATA_SOURCE_NAME)
    public DataSource routingDataSource(
            @Qualifier(WRITE_DATA_SOURCE_NAME) DataSource writeDataSource,
            @Qualifier(READ_ONLY_DATA_SOURCE_NAME) DataSource readOnlyDataSource
    ) {
        return new WriteReadOnlyRoutingDataSource(writeDataSource, readOnlyDataSource);
    }

    @Primary
    @Bean(name = MAIN_DATA_SOURCE_NAME)
    public LazyConnectionDataSourceProxy dataSource(
            @Qualifier(ROUTING_DATA_SOURCE_NAME) DataSource routingDataSource
    ) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
}
