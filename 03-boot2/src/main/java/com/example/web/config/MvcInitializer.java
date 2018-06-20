package com.example.web.config;

import com.example.persistence.config.DataSourceConfig;
import com.example.persistence.config.JdbcConfig;
import com.example.security.config.SecurityConfig;
import com.example.service.config.ServiceConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

// TODO 5-08 このクラスを削除する
public class MvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    // getRootConfigClasses()をオーバーライドしてnullをreturnする
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{DataSourceConfig.class, JdbcConfig.class, ServiceConfig.class,
                MvcConfig.class, SecurityConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{ "/" };
    }
}
