package com.example.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

// TODO 2-08 Java Configであることを示すアノテーションを付加する

// TODO 2-09 @Serviceクラスをコンポーネントスキャンするアノテーションを付加する

// TODO 2-10 トランザクション管理を有効化するアノテーションを付加する

public class ServiceConfig {

    // TODO 2-11 Beanであることを示すアノテーションを付加する

    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        // DataSourceTransactionManagerをnewしてreturnする
        // コンストラクタでDataSourceを受け取る
        return new DataSourceTransactionManager(dataSource);
    }
}
