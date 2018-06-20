package com.example.web.config;

import com.example.persistence.config.DataSourceConfig;
import com.example.persistence.config.JdbcConfig;
import com.example.security.config.SecurityConfig;
import com.example.service.config.ServiceConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

// TODO 3-34 コメントを外した後このテストを実行して、MvcInitializerの実装が正しいかチェックする（テストがグリーンになればOK）
public class MvcInitializerTest {

    MvcInitializer mvcInitializer = new MvcInitializer();

//    @Test
//    @DisplayName("AbstractAnnotationConfigDispatcherServletInitializerを継承している")
//    public void classTest() {
//        assertTrue(mvcInitializer instanceof AbstractAnnotationConfigDispatcherServletInitializer);
//    }
//
//    @Test
//    @DisplayName("getRootConfigClasses()がnullを返す")
//    public void getRootConfigClassesTest() {
//        assertNull(mvcInitializer.getRootConfigClasses(), "must be null");
//    }
//
//    @Test
//    @DisplayName("getServletConfigClasses()がJava Configの配列を返す")
//    public void getServletConfigClassesTest() {
//        Class<?>[] configClasses = mvcInitializer.getServletConfigClasses();
//        Class[] expectedClasses = {DataSourceConfig.class, JdbcConfig.class, ServiceConfig.class,
//                MvcConfig.class, SecurityConfig.class};
//        Comparator<Class> comparator = (c1, c2) -> c1.getSimpleName().compareTo(c2.getSimpleName());
//        Arrays.sort(configClasses, comparator);
//        Arrays.sort(expectedClasses, comparator);
//        assertArrayEquals(expectedClasses, configClasses, "config classes not correct");
//    }
//
//    @Test
//    @DisplayName("getServletMappings()が/を返す")
//    public void getServletMappingsTest() {
//        String[] servletMappings = mvcInitializer.getServletMappings();
//        assertArrayEquals(new String[]{"/"}, servletMappings, "servlet mapping not correct");
//    }
//
//    @Test
//    @DisplayName("getServletFilters()はオーバーライドされていない")
//    public void getServletFiltersTest() {
//        assertThrows(NoSuchMethodException.class,
//                () -> MvcInitializer.class.getDeclaredMethod("getServletFilters"),
//                "must not be defined");
//    }
}
