package com.example.persistence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.DataAccessStrategy;
import org.springframework.data.jdbc.core.DefaultDataAccessStrategy;
import org.springframework.data.jdbc.core.SqlGeneratorSource;
import org.springframework.data.jdbc.mapping.model.DelimiterNamingStrategy;
import org.springframework.data.jdbc.mapping.model.JdbcMappingContext;
import org.springframework.data.jdbc.mapping.model.NamingStrategy;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
// TODO 5-04 @ComponentScanアノテーションのみ削除する
@EnableJdbcRepositories(basePackages = "com.example.persistence.repository")
public class JdbcConfig {

    // TODO 5-05 NamedParameterJdbcTemplateのBeanのみ削除する

    // このBeanは削除しない（Spring Data JDBCはAuto Configuration対象ではないため）
    @Bean
    public DataAccessStrategy dataAccessStrategy(JdbcMappingContext context) {
        // NamedParameterJdbcTemplateを利用したDataAccessStrategy
        return new DefaultDataAccessStrategy(new SqlGeneratorSource(context), context);
    }

    // このBeanは削除しない（Spring Data JDBCはAuto Configuration対象ではないため）
    @Bean
    public NamingStrategy namingStrategy() {
        // フィールド名のキャメルケースを列名のスネークケースに変換するNamingStrategy
        return new DelimiterNamingStrategy();
    }

}
