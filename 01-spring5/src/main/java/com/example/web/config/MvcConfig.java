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
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.nio.charset.StandardCharsets;

// TODO 3-09 Java Configであることを示すアノテーションを付加されていることを確認する（変更不要）
@Configuration
// TODO 3-10 Spring MVCを有効化するアノテーションを付加する

// TODO 3-11 コントローラークラスをコンポーネントスキャンしていることを確認する（変更不要）
@ComponentScan(basePackages = {"com.example.web.controller"})
// TODO 3-12 WebMvcConfigurerインタフェースを実装する
public class MvcConfig   {

    // TODO 3-13 SpringResourceTemplateResolverをBean定義する
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver =
                new SpringResourceTemplateResolver();
        // ビューを保存するフォルダ名を「classpath:/templates/」、拡張子を「.html」に指定する


        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    // TODO 3-14 SpringTemplateEngineのBean定義を確認する（変更不要）
    @Bean
    public SpringTemplateEngine templateEngine(SpringResourceTemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        // SpringResourceTemplateResolverをセットする
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.setEnableSpringELCompiler(true);
        // ThymeleafでDate and Time APIを利用するDialectを追加
        templateEngine.addDialect(new Java8TimeDialect());
        // ThymeleafでSpring Securityを利用するDialectを追加
        templateEngine.addDialect(new SpringSecurityDialect());
        return templateEngine;
    }

    // TODO 3-15 ThymeleafViewResolverBean定義を確認する（変更不要）
    @Bean
    public ThymeleafViewResolver viewResolver(SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine);
        viewResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        return viewResolver;
    }

    // TODO 3-16 addResourceHandlers()をオーバーライドして「/css/**」へのリクエストを「classpath:/static/css/」にルーティングする




    // TODO 3-17 MessageSourceをBean定義する
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        // メッセージを記述するプロパティファイル名を「messages」に指定する

        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        return messageSource;
    }
}
