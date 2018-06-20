package com.example.persistence.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class DataSourceConfigTest {

    @Test
    @DisplayName("クラスに必要なアノテーションが付加されている")
    public void annotationTest() {
        Configuration configuration = DataSourceConfig.class.getAnnotation(Configuration.class);
        assertNotNull(configuration, "annotation required");
    }

    @Test
    @DisplayName("DataSourceのBeanが正しく定義されている")
    public void dataSourceTest() {
        Method dataSourceMethod = assertDoesNotThrow(() -> DataSourceConfig.class.getMethod("dataSource"));
        assertNotNull(dataSourceMethod.getAnnotation(Bean.class), "annotation required");
        EmbeddedDatabase dataSource = new DataSourceConfig().dataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        assertAll("SQL files are not executed correctly",
                () -> assertEquals(5, JdbcTestUtils.countRowsInTable(jdbcTemplate, "customer")),
                () -> assertEquals(3, JdbcTestUtils.countRowsInTable(jdbcTemplate, "account")),
                () -> assertEquals(3, JdbcTestUtils.countRowsInTable(jdbcTemplate, "account_authority"))
        );
    }
}
