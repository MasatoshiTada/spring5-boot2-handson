package com.example.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "com.example.service.impl")
@EnableTransactionManagement
public class ServiceConfig {

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        // DataSourceTransactionManagerをnewしてreturnする
        // コンストラクタでDataSourceを受け取る
        return new DataSourceTransactionManager(dataSource);
    }
}
