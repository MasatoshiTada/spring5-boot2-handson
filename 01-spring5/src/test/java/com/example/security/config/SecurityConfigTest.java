package com.example.security.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/* TODO 4-21 コメントを外してからこのテストを実行して、SecurityConfigの実装が正しいかチェックする
 * configure(WebSecurity)・configure(HttpSecurity)は、実装の正しさをチェックできません。
 * 後ほどブラウザから実行してチェックします。
 */
public class SecurityConfigTest {

//    SecurityConfig securityConfig = new SecurityConfig();
//
//    @Test
//    @DisplayName("WebSecurityConfigurerAdapterを継承している")
//    public void classTest() {
//        assertTrue(securityConfig instanceof WebSecurityConfigurerAdapter);
//    }
//
//    @Test
//    @DisplayName("クラスに必要なアノテーションが付加されている")
//    public void annotationTest() {
//        EnableWebSecurity enableWebSecurity = SecurityConfig.class.getAnnotation(EnableWebSecurity.class);
//        ComponentScan componentScan = SecurityConfig.class.getAnnotation(ComponentScan.class);
//        assertAll("annotation required",
//                () -> assertNotNull(enableWebSecurity),
//                () -> assertNotNull(componentScan),
//                () -> assertArrayEquals(new String[]{"com.example.security.details"},
//                        componentScan.basePackages())
//        );
//    }
//
//    @Test
//    @DisplayName("configure(WebSecurity)の存在のみチェック（注意！実装の正しさはチェックしてません）")
//    public void configureWebSecurityTest() {
//        assertDoesNotThrow(
//                () -> SecurityConfig.class.getMethod("configure", WebSecurity.class),
//                "method is not defined");
//    }
//
//    @Test
//    @DisplayName("configure(HttpSecurity)の存在のみチェック（注意！実装の正しさはチェックしてません）")
//    public void configureHttpSecurityTest() {
//        assertDoesNotThrow(
//                () -> SecurityConfig.class.getDeclaredMethod("configure", HttpSecurity.class),
//                "method is not defined");
//    }
//
//    @Test
//    @DisplayName("PasswordEncoderのBeanが定義されている")
//    public void passwordEncoderTest() {
//        Method passwordEncoderMethod = assertDoesNotThrow(
//                () -> SecurityConfig.class.getMethod("passwordEncoder"));
//        Bean bean = passwordEncoderMethod.getAnnotation(Bean.class);
//        assertNotNull(bean);
//        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
//        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder);
//    }
}
