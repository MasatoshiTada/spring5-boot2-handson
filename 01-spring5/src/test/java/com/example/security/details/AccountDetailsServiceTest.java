package com.example.security.details;

import com.example.persistence.config.DataSourceConfig;
import com.example.persistence.config.JdbcConfig;
import com.example.persistence.entity.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

// TODO 4-21 このテストを実行して、AccountDetailsServiceの実装が正しいかチェックする
@SpringJUnitConfig(classes = {DataSourceConfig.class, JdbcConfig.class,
        AccountDetailsServiceTest.SecurityConfig.class})
public class AccountDetailsServiceTest {

    @Autowired
    AccountDetailsService accountDetailsService;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    @DisplayName("user@example.comでユーザーを検索できる")
    public void loadUserByUsernameTest1() {
        AccountDetails accountDetails = (AccountDetails) accountDetailsService.loadUserByUsername("user@example.com");
        Account account = accountDetails.getAccount();
        assertNotNull(account);
        List<String> authorities = account.getAuthorities();
        assertEquals(1, authorities.size());
        assertAll(
                () -> assertEquals(new Integer(1), account.getId()),
                () -> assertEquals("user", account.getName()),
                () -> assertEquals("user@example.com", account.getEmail()),
                () -> assertTrue(passwordEncoder.matches("user", account.getPassword())),
                () -> assertTrue(accountDetails.isAccountNonExpired()),
                () -> assertTrue(accountDetails.isAccountNonLocked()),
                () -> assertTrue(accountDetails.isCredentialsNonExpired()),
                () -> assertTrue(accountDetails.isEnabled())
        );
    }

    @Test
    @DisplayName("hogehoge@hoge.comで例外発生")
    public void loadUserByUsernameTest2() {
        assertThrows(UsernameNotFoundException.class, () -> {
            accountDetailsService.loadUserByUsername("hogehoge@hoge.com");
        });
    }

    @Configuration
    @ComponentScan("com.example.security.details")
    static class SecurityConfig {

    }
}
