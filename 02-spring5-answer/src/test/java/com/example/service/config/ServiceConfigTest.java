package com.example.service.config;

import com.example.persistence.config.DataSourceConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceConfigTest {

    ServiceConfig serviceConfig = new ServiceConfig();
    DataSourceConfig dataSourceConfig = new DataSourceConfig();

    @Test
    @DisplayName("クラスに必要なアノテーションが付加されている")
    public void annotationTest() {
        Configuration configuration = ServiceConfig.class.getAnnotation(Configuration.class);
        ComponentScan componentScan = ServiceConfig.class.getAnnotation(ComponentScan.class);
        EnableTransactionManagement enableTransactionManagement = ServiceConfig.class.getAnnotation(EnableTransactionManagement.class);
        assertAll("annotations required",
                () -> assertNotNull(configuration),
                () -> assertNotNull(componentScan),
                () -> assertNotNull(enableTransactionManagement),
                () -> assertArrayEquals(new String[]{"com.example.service.impl"},
                        componentScan.basePackages())
        );
    }

    @Test
    @DisplayName("transactionManagerのBeanが定義されている")
    public void transactionManagerTest() {
        Method transactionManagerMethod = assertDoesNotThrow(() -> ServiceConfig.class.getMethod("transactionManager", DataSource.class));
        Bean bean = transactionManagerMethod.getAnnotation(Bean.class);
        assertNotNull(bean, "annotation required");

        PlatformTransactionManager transactionManager = serviceConfig.transactionManager(dataSourceConfig.dataSource());
        assertTrue(transactionManager instanceof DataSourceTransactionManager, "not correct transaction manager");
    }
}
