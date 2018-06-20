package com.example.web.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractResourceBasedMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

// TODO 3-35 このテストを実行して、MvcConfigの実装が正しいかチェックする（addResourceHandlers()のみ、実装の正しさをチェックできません。後ほどブラウザから実行してチェックします）
public class MvcConfigTest {

    MvcConfig mvcConfig = new MvcConfig();

    @Test
    @DisplayName("WebMvcConfigurerを実装している")
    public void classTest() {
        assertTrue(mvcConfig instanceof WebMvcConfigurer);
    }

    @Test
    @DisplayName("クラスに必要なアノテーションが付加されている")
    public void annotationTest() {
        EnableWebMvc enableWebMvc = MvcConfig.class.getAnnotation(EnableWebMvc.class);
        Configuration configuration = MvcConfig.class.getAnnotation(Configuration.class);
        ComponentScan componentScan = MvcConfig.class.getAnnotation(ComponentScan.class);
        assertAll("",
                () -> assertNotNull(enableWebMvc),
                () -> assertNotNull(configuration),
                () -> assertNotNull(componentScan),
                () -> assertArrayEquals(new String[]{"com.example.web.controller"},
                        componentScan.basePackages())
        );
    }

    @Test
    @DisplayName("templateResolverのBeanが定義されている")
    public void templateResolverTest() {
        Method templateResolverMethod = assertDoesNotThrow(() -> MvcConfig.class.getMethod("templateResolver"));
        Bean bean = templateResolverMethod.getAnnotation(Bean.class);
        assertNotNull(bean);

        SpringResourceTemplateResolver templateResolver = mvcConfig.templateResolver();
        assertAll("templateResolver not configured correctly",
                () -> assertEquals("classpath:/templates/", templateResolver.getPrefix()),
                () -> assertEquals(".html", templateResolver.getSuffix()),
                () -> assertEquals(TemplateMode.HTML, templateResolver.getTemplateMode()),
                () -> assertEquals(StandardCharsets.UTF_8.name(),
                        templateResolver.getCharacterEncoding()),
                () -> assertEquals(false, templateResolver.isCacheable())
        );
    }

    @Test
    @DisplayName("templateEngineのBeanが定義されている")
    public void templateEngineTest() {
        Method templateEngineMethod = assertDoesNotThrow(() -> MvcConfig.class.getMethod("templateEngine", SpringResourceTemplateResolver.class));
        Bean bean = templateEngineMethod.getAnnotation(Bean.class);
        assertNotNull(bean);

        SpringResourceTemplateResolver templateResolver = mvcConfig.templateResolver();
        SpringTemplateEngine templateEngine = mvcConfig.templateEngine(templateResolver);
        Set<ITemplateResolver> templateResolvers = templateEngine.getTemplateResolvers();
        String[] actualDialects = templateEngine.getDialects()
                .stream()
                .map(dialect -> dialect.getClass().getSimpleName())
                .sorted()
                .toArray(String[]::new);
        assertAll("templateEngine not configured correctly",
                () -> assertTrue(templateResolvers.contains(templateResolver)),
                () -> assertTrue(templateEngine.getEnableSpringELCompiler()),
                () -> assertArrayEquals(
                        new String[]{"Java8TimeDialect", "SpringSecurityDialect", "SpringStandardDialect"},
                        actualDialects)
        );
    }

    @Test
    @DisplayName("viewResolverのBeanが定義されている")
    public void viewResolverTest() {
        Method viewResolverMethod = assertDoesNotThrow(() -> MvcConfig.class.getMethod("viewResolver", SpringTemplateEngine.class));
        Bean bean = viewResolverMethod.getAnnotation(Bean.class);
        assertNotNull(bean);

        ThymeleafViewResolver viewResolver = mvcConfig.viewResolver(mvcConfig.templateEngine(mvcConfig.templateResolver()));
        ISpringTemplateEngine templateEngine = viewResolver.getTemplateEngine();
        assertAll("viewResolver not configured correctly",
                () -> assertTrue(templateEngine instanceof SpringTemplateEngine),
                () -> assertEquals(StandardCharsets.UTF_8.name(), viewResolver.getCharacterEncoding())
        );
    }

    @Test
    @DisplayName("addResourceHandlers()の存在のみチェック（注意！実装の正しさはチェックしていません）")
    public void addResourceHandlersTest() {
        assertDoesNotThrow(() -> MvcConfig.class.getMethod("addResourceHandlers", ResourceHandlerRegistry.class));
    }

    @Test
    @DisplayName("messageSourceのBeanが定義されている")
    public void messageSourceTest() {
        Method messageSourceMethod = assertDoesNotThrow(() -> MvcConfig.class.getMethod("messageSource"));
        Bean bean = messageSourceMethod.getAnnotation(Bean.class);
        assertNotNull(bean);

        MessageSource messageSource = mvcConfig.messageSource();
        assertTrue(messageSource instanceof ResourceBundleMessageSource);
        ResourceBundleMessageSource resourceBundleMessageSource = (ResourceBundleMessageSource) messageSource;
        assertAll("messageSource not configured correctly",
                () -> resourceBundleMessageSource.getBasenameSet().contains("messages"),
                () -> {
                    Field defaultEncodingField = AbstractResourceBasedMessageSource.class.getDeclaredField("defaultEncoding");
                    defaultEncodingField.setAccessible(true);
                    assertEquals(StandardCharsets.UTF_8.name(),
                            defaultEncodingField.get(resourceBundleMessageSource));
                }
        );
    }
}
