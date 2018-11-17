package com.example.persistence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

// TODO 5-05 このクラスを削除する
@Configuration
@ComponentScan(basePackages = "com.example.persistence.repository.impl")
@EnableJdbcRepositories(basePackages = "com.example.persistence.repository")
public class JdbcConfig {

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        // コンストラクタでDataSourceを受け取る
        return new NamedParameterJdbcTemplate(dataSource);
    }

}
