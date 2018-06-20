package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// TODO 4-02 Spring Securityを有効化するアノテーションを付加する

// TODO 4-03 com.example.security.detailsパッケージをコンポーネントスキャンする

// TODO 4-04 WebSecurityConfigurerAdapterを継承する
public class SecurityConfig    {

    // TODO 4-05 configure(WebSecurity)をオーバーライドして、「/css/**」をセキュリティの除外対象にする

    public void configure(WebSecurity web) throws Exception {

    }

    // TODO 4-06 configure(HttpSecurity)をオーバーライドして、認証認可設定を記述する

    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                // TODO 4-06-1 ログインページとして「/login」を指定
                .loginPage(null)
                .permitAll();
        http.authorizeRequests()
                // TODO 4-06-2 「/insert*」はADMINロールのみアクセス可能に指定
                .mvcMatchers(null).hasRole(null)
                // その他のURLは認証済みであればアクセス可能
                .anyRequest().authenticated();
        http.logout()
                .invalidateHttpSession(true)
                .permitAll();
    }

    // TODO 4-07 Beanであることを示すアノテーションを付加する

    public PasswordEncoder passwordEncoder() {
        // TODO 4-08 BCryptPasswordEncoderをnewしてreturnする
        return null;
    }
}
