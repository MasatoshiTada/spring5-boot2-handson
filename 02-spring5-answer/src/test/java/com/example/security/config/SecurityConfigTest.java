package com.example.security.config;

import com.example.persistence.config.DataSourceConfig;
import com.example.persistence.config.JdbcConfig;
import com.example.service.config.ServiceConfig;
import com.example.web.config.MvcConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

public class SecurityConfigTest {

    @Nested
    @DisplayName("クラス定義のテスト")
    class ClassDefinitionTest {

        SecurityConfig securityConfig = new SecurityConfig();

        @Test
        @DisplayName("WebSecurityConfigurerAdapterを継承している")
        public void classTest() {
            Object obj = securityConfig;
            assertTrue(obj instanceof WebSecurityConfigurerAdapter);
        }

        @Test
        @DisplayName("クラスに必要なアノテーションが付加されている")
        public void annotationTest() {
            EnableWebSecurity enableWebSecurity = SecurityConfig.class.getAnnotation(EnableWebSecurity.class);
            ComponentScan componentScan = SecurityConfig.class.getAnnotation(ComponentScan.class);
            assertAll("annotation required",
                    () -> assertNotNull(enableWebSecurity),
                    () -> assertNotNull(componentScan),
                    () -> assertArrayEquals(new String[]{"com.example.security.details"},
                            componentScan.basePackages())
            );
        }

        @Test
        @DisplayName("configure(WebSecurity)の存在のみチェック（注意！実装の正しさはチェックしてません）")
        public void configureWebSecurityTest() {
            assertDoesNotThrow(
                    () -> SecurityConfig.class.getMethod("configure", WebSecurity.class),
                    "method is not defined");
        }

        @Test
        @DisplayName("configure(HttpSecurity)の存在のみチェック（注意！実装の正しさはチェックしてません）")
        public void configureHttpSecurityTest() {
            assertDoesNotThrow(
                    () -> SecurityConfig.class.getDeclaredMethod("configure", HttpSecurity.class),
                    "method is not defined");
        }

        @Test
        @DisplayName("PasswordEncoderのBeanが定義されている")
        public void passwordEncoderTest() {
            Method passwordEncoderMethod = assertDoesNotThrow(
                    () -> SecurityConfig.class.getMethod("passwordEncoder"));
            Bean bean = passwordEncoderMethod.getAnnotation(Bean.class);
            assertNotNull(bean);
            PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
            assertTrue(passwordEncoder instanceof BCryptPasswordEncoder);
        }

    }

    @Nested
    @DisplayName("セキュリティ設定のテスト")
    @SpringJUnitWebConfig(classes = {DataSourceConfig.class, JdbcConfig.class,
            ServiceConfig.class,MvcConfig.class, SecurityConfig.class})
    class ConfigurationTest {

        MockMvc mvc;

        @BeforeEach
        void setup(WebApplicationContext context) {
            mvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .apply(springSecurity())
                    .build();
        }

        @Test
        @DisplayName("style.cssがログイン無しで取得できる")
        @WithAnonymousUser
        void getCss() throws Exception {
            mvc.perform(get("/css/style.css"))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("user@example.com/userでログインできる")
        void userLogin() throws Exception {
            mvc.perform(formLogin("/login").user("user@example.com").password("user"))
                    .andExpect(authenticated());
        }

        @Test
        @DisplayName("admin@example.com/adminでログインできる")
        void adminLogin() throws Exception {
            mvc.perform(formLogin("/login").user("admin@example.com").password("admin"))
                    .andExpect(authenticated());
        }

        @Test
        @DisplayName("ログアウト後にログイン画面にリダイレクトされる")
        void logoutRedirectLogin() throws Exception {
            mvc.perform(logout())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(header().stringValues("location", "/login?logout"));
        }

        // 一覧画面でユーザー名を表示しているためUserDetailsの作成が必要
        @Test
        @WithUserDetails(value = "user@example.com", userDetailsServiceBeanName = "accountDetailsService")
        @DisplayName("USERロールで一覧画面にアクセスできる")
        void topWithUser() throws Exception {
            mvc.perform(get("/"))
                    .andExpect(status().isOk());
        }

        // 一覧画面でユーザー名を表示しているためUserDetailsの作成が必要
        @Test
        @WithUserDetails(value = "admin@example.com", userDetailsServiceBeanName = "accountDetailsService")
        @DisplayName("ADMINロールで一覧画面にアクセスできる")
        void topWithAdmin() throws Exception {
            mvc.perform(get("/"))
                    .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(username = "user@example.com", roles = {"USER"})
        @DisplayName("USERロールで追加画面にアクセスできない")
        void insertMainWithUser() throws Exception {
            mvc.perform(get("/insertMain"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
        @DisplayName("ADMINロールで追加画面にアクセスできる")
        void insertMainWithAdmin() throws Exception {
            mvc.perform(get("/insertMain"))
                    .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(username = "user@example.com", roles = {"USER"})
        @DisplayName("USERロールで追加完了にアクセスできない")
        void insertCompleteWithUser() throws Exception {
            mvc.perform(post("/insertComplete").with(csrf()))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
        @DisplayName("ADMINロールで追加完了にアクセスできる")
        void insertCompleteWithAdmin() throws Exception {
            mvc.perform(post("/insertComplete")
                    .with(csrf())
                    .param("firstName", "佑唯")
                    .param("lastName", "今泉")
                    .param("email", "yimaizumi@keyaki.com")
                    .param("birthday", "1998-09-30"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(header().stringValues("location", "/"));
        }
    }

}
