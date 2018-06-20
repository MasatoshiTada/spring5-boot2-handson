package com.example.persistence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.nio.charset.StandardCharsets;

@Configuration
public class DataSourceConfig {

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
