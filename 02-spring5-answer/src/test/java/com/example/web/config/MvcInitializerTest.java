package com.example.web.config;

import com.example.persistence.config.DataSourceConfig;
import com.example.persistence.config.JdbcConfig;
import com.example.security.config.SecurityConfig;
import com.example.service.config.ServiceConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import static org.junit.jupiter.api.Assertions.*;

/* TODO 3-34 このテストを実行して、MvcInitializerの実装が正しいかチェックする（テストがグリーンになればOK）
 *  - テスト実行前に
 *      - getServletConfigClassesTest_withSpringSecurity()メソッドをテスト対象から外すために、@Disabledを付加してください
 */
/* TODO 4-23 このテストを実行して、MvcInitializerの実装が正しいかチェックする（テストがグリーンになればOK）
 *  - テスト実行前に
 *      - getServletConfigClassesTest_withSpringSecurity()メソッドをテスト対象にするために、@Disabledを削除してください
 *      - getServletConfigClassesTest()メソッドをテスト対象から外すために、@Disabledを付加してください
 */
public class MvcInitializerTest {

    MvcInitializer mvcInitializer = new MvcInitializer();

    @Test
    @DisplayName("AbstractAnnotationConfigDispatcherServletInitializerを継承している")
    public void classTest() {
        Object obj = mvcInitializer; // 未継承時のコンパイルエラー回避のため一旦Object型変数に代入
        assertTrue(obj instanceof AbstractAnnotationConfigDispatcherServletInitializer);
    }

    @Test
    @DisplayName("getRootConfigClasses()がnullを返す")
    public void getRootConfigClassesTest() {
        assertNull(mvcInitializer.getRootConfigClasses(), "must be null");
    }

    @Test
    @DisplayName("[Spring Security無し] getServletConfigClasses()がJava Configの配列を返す")
    // 演習3の時点ではこちらを有効化（演習4ではこちらを無効化）
    @Disabled("Spring Security有りでテストするため無効化")
    public void getServletConfigClassesTest() {
        Class[] expectedClasses = {DataSourceConfig.class, JdbcConfig.class, ServiceConfig.class, MvcConfig.class};
        Class<?>[] configClasses = mvcInitializer.getServletConfigClasses();
        assertArrayEquals(expectedClasses, configClasses, "config classes not correct");
    }

    @Test
    @DisplayName("[Spring Security有り] getServletConfigClasses()がJava Configの配列を返す")
    // 演習3の時点ではこちらを無効化（演習4ではこちらを有効化）
//    @Disabled("Spring Security無しでテストするため無効化")
    public void getServletConfigClassesTest_withSpringSecurity() {
        Class[] expectedClasses = {DataSourceConfig.class, JdbcConfig.class, ServiceConfig.class, MvcConfig.class, SecurityConfig.class};
        Class<?>[] configClasses = mvcInitializer.getServletConfigClasses();
        assertArrayEquals(expectedClasses, configClasses, "config classes not correct");
    }

    @Test
    @DisplayName("getServletMappings()が/を返す")
    public void getServletMappingsTest() {
        String[] servletMappings = mvcInitializer.getServletMappings();
        assertArrayEquals(new String[]{"/"}, servletMappings, "servlet mapping not correct");
    }

    @Test
    @DisplayName("getServletFilters()はオーバーライドされていない")
    public void getServletFiltersTest() {
        assertThrows(NoSuchMethodException.class,
                () -> MvcInitializer.class.getDeclaredMethod("getServletFilters"),
                "must not be defined");
    }
}
