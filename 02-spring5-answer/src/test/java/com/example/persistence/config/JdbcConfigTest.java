package com.example.persistence.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jdbc.repository.config.JdbcConfiguration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcConfigTest {

    JdbcConfig jdbcConfig = new JdbcConfig();

    DataSource dataSource = new DataSourceConfig().dataSource();

    @Test
    @DisplayName("クラスがJdbcConfigurationを継承している")
    public void classTest() {
        assertTrue(jdbcConfig instanceof JdbcConfiguration);
    }

    @Test
    @DisplayName("クラスに必要なアノテーションが付加されている")
    public void annotationTest() {
        Configuration configuration = JdbcConfig.class.getAnnotation(Configuration.class);
        ComponentScan componentScan = JdbcConfig.class.getAnnotation(ComponentScan.class);
        EnableJdbcRepositories enableJdbcRepositories = JdbcConfig.class.getAnnotation(EnableJdbcRepositories.class);
        assertAll("annotations required",
                () -> assertNotNull(configuration),
                () -> assertNotNull(componentScan),
                () -> assertNotNull(enableJdbcRepositories),
                () -> assertArrayEquals(new String[]{"com.example.persistence.repository.impl"},
                        componentScan.basePackages()),
                () -> assertArrayEquals(new String[]{"com.example.persistence.repository"},
                        enableJdbcRepositories.basePackages())
        );
    }

    @Test
    @DisplayName("NamedParameterJdbcTemplateのBeanが定義されている")
    public void namedParameterJdbcTemplateTest() {
        Method namedParameterJdbcTemplateMethod =
                assertDoesNotThrow(() -> JdbcConfig.class.getMethod("namedParameterJdbcTemplate", DataSource.class));
        Bean bean = namedParameterJdbcTemplateMethod.getAnnotation(Bean.class);
        assertNotNull(bean);

        NamedParameterJdbcTemplate jdbcTemplate =
                jdbcConfig.namedParameterJdbcTemplate(dataSource);
        assertNotNull(jdbcTemplate);
    }

}
