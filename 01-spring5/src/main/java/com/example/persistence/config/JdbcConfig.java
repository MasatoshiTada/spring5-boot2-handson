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

// TODO 1-08 Java Configであることを示すアノテーションを付加する

// @Repositoryで定義したリポジトリクラスのパッケージ名を指定する
@ComponentScan(basePackages = "com.example.persistence.repository.impl")
// TODO 1-09 CrudRepositoryを継承したインタフェースのパッケージ名を指定する

public class JdbcConfig {

    // 引数でDataSourceのBeanを受け取る
    // TODO 1-10 Beanであることを示すアノテーションを付加する

    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        // コンストラクタでDataSourceを受け取る
        return new NamedParameterJdbcTemplate(dataSource);
    }

    // TODO 1-11 Beanであることを示すアノテーションを付加する

    public DataAccessStrategy dataAccessStrategy(JdbcMappingContext context) {
        // NamedParameterJdbcTemplateを利用したDataAccessStrategy
        return new DefaultDataAccessStrategy(new SqlGeneratorSource(context), context);
    }

    // TODO 1-12 Beanであることを示すアノテーションを付加する

    public NamingStrategy namingStrategy() {
        // フィールド名のキャメルケースを列名のスネークケースに変換するNamingStrategy
        return new DelimiterNamingStrategy();
    }

}
