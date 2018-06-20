package com.example.persistence.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.DataAccessStrategy;
import org.springframework.data.jdbc.core.DefaultDataAccessStrategy;
import org.springframework.data.jdbc.mapping.model.DelimiterNamingStrategy;
import org.springframework.data.jdbc.mapping.model.JdbcMappingContext;
import org.springframework.data.jdbc.mapping.model.NamingStrategy;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcConfigTest {

    JdbcConfig jdbcConfig = new JdbcConfig();

    DataSource dataSource = new DataSourceConfig().dataSource();

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

    @Test
    @DisplayName("DataAccessStrategyのBeanが定義されている")
    public void dataAccessStrategyTest() {
        Method dataAccessStrategyMethod =
                assertDoesNotThrow(() -> JdbcConfig.class.getMethod("dataAccessStrategy", JdbcMappingContext.class));
        Bean bean = dataAccessStrategyMethod.getAnnotation(Bean.class);
        assertNotNull(bean);

        DataAccessStrategy dataAccessStrategy = jdbcConfig.dataAccessStrategy(
                new JdbcMappingContext(jdbcConfig.namedParameterJdbcTemplate(dataSource))
        );
        assertTrue(dataAccessStrategy instanceof DefaultDataAccessStrategy);
    }


    @Test
    @DisplayName("NamingStrategyのBeanが定義されている")
    public void namingStrategyTest() {
        Method namingStrategyMethod =
                assertDoesNotThrow(() -> JdbcConfig.class.getMethod("namingStrategy"));
        Bean bean = namingStrategyMethod.getAnnotation(Bean.class);
        assertNotNull(bean);

        NamingStrategy namingStrategy = jdbcConfig.namingStrategy();
        assertNotNull(namingStrategy);
        assertTrue(namingStrategy instanceof DelimiterNamingStrategy);
    }

}
