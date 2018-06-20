package com.example.web.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.nio.charset.StandardCharsets;

// TODO 3-19 Java Configであることを示すアノテーションを付加する
@Configuration
// TODO 3-20 Spring MVCを有効化するアノテーションを付加する
@EnableWebMvc
// TODO 3-21 アノテーションを付加してコントローラークラスをコンポーネントスキャンする
@ComponentScan(basePackages = {"com.example.web.controller"})
// TODO 3-22 WebMvcConfigurerインタフェースを実装する
public class MvcConfig implements WebMvcConfigurer {

    // TODO 3-23 Beanであることを示すアノテーションを付加する
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver =
                new SpringResourceTemplateResolver();
        // TODO 3-24 ビューを保存するフォルダ名を「classpath:/templates/」に指定する
        templateResolver.setPrefix("classpath:/templates/");
        // TODO 3-25 ビューの拡張子を「.html」に指定する
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    // TODO 3-26 Beanであることを示すアノテーションを付加する
    @Bean
    public SpringTemplateEngine templateEngine(SpringResourceTemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.setEnableSpringELCompiler(true);
        // ThymeleafでDate and Time APIを利用するDialectを追加
        templateEngine.addDialect(new Java8TimeDialect());
        // ThymeleafでSpring Securityを利用するDialectを追加
        templateEngine.addDialect(new SpringSecurityDialect());
        return templateEngine;
    }

    // TODO 3-27 Beanであることを示すアノテーションを付加する
    @Bean
    public ThymeleafViewResolver viewResolver(SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine);
        viewResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        return viewResolver;
    }

    // TODO 3-28 addResourceHandlers()をオーバーライドして「/css/**」へのリクエストを「classpath:/static/css/」にルーティングする
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
    }

    // TODO 3-29 Beanであることを示すアノテーションを付加する
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        // TODO 3-30 メッセージを記述するプロパティファイル名を「messages」に指定する
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        return messageSource;
    }
}
