package com.example.persistence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.nio.charset.StandardCharsets;

// TODO 1-03 Java Configであることを示すアノテーションを付加する
@Configuration
public class DataSourceConfig {

    // TODO 1-04 Beanであることを示すアノテーションを付加する
    @Bean
    public EmbeddedDatabase dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setScriptEncoding(StandardCharsets.UTF_8.name())
                // SQLファイル名を指定する（src/main/resources直下）
                .addScript("classpath:/schema.sql")
                .addScript("classpath:/data.sql")
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }
}
