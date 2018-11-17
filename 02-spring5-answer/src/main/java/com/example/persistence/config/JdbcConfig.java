package com.example.persistence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jdbc.repository.config.JdbcConfiguration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
// @Repositoryで定義したリポジトリクラスのパッケージ名を指定する
@ComponentScan(basePackages = "com.example.persistence.repository.impl")
@EnableJdbcRepositories(basePackages = "com.example.persistence.repository")
public class JdbcConfig extends JdbcConfiguration {

    // 引数でDataSourceのBeanを受け取る
    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        // コンストラクタでDataSourceを受け取る
        return new NamedParameterJdbcTemplate(dataSource);
    }

}
