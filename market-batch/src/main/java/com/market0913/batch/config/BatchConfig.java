package com.market0913.batch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing(dataSourceRef = "batchDataSource", transactionManagerRef = "batchTransactionManager")
@RequiredArgsConstructor
public class BatchConfig {

    @Value("${spring.datasource.driver-class-name}")
    String driverClassName;

    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.username}")
    String userName;

    @Value("${spring.datasource.password}")
    String password;

    @Bean
    public DataSource batchDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public JpaTransactionManager batchTransactionManager() {
        return new JpaTransactionManager();
    }
}
