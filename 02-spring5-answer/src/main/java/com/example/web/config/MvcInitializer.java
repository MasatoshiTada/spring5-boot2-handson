package com.example.web.config;

import com.example.persistence.config.DataSourceConfig;
import com.example.persistence.config.JdbcConfig;
import com.example.security.config.SecurityConfig;
import com.example.service.config.ServiceConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * このクラスを作成するだけで、DispatcherServletがサーブレットコンテナに登録されます。
 * Servlet 3.0から導入されたServlet Initializerの機能を利用しています。
 */
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
