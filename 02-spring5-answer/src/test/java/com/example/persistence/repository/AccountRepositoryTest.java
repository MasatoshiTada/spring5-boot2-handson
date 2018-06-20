package com.example.persistence.repository;

import com.example.persistence.config.DataSourceConfig;
import com.example.persistence.config.JdbcConfig;
import com.example.persistence.entity.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = {DataSourceConfig.class, JdbcConfig.class})
public class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    @DisplayName("email = user@example.comでuserがロール付きで取得できる")
    public void findByEmailTest1() {
        Optional<Account> accountOptional = accountRepository.findByEmail("user@example.com");
        assertTrue(accountOptional.isPresent());
        Account account = accountOptional.get();
        assertAll(
                () -> assertEquals(new Integer(1), account.getId()),
                () -> assertEquals("user", account.getName()),
                () -> assertEquals("user@example.com", account.getEmail()),
                () -> assertTrue(passwordEncoder.matches("user", account.getPassword())),
                () -> assertEquals(1, account.getAuthorities().size()),
                () -> assertEquals("ROLE_USER", account.getAuthorities().get(0))
        );
    }

    @Test
    @DisplayName("email = hogehoge@hoge.comで該当ユーザーなし")
    public void findByEmailTest2() {
        Optional<Account> accountOptional = accountRepository.findByEmail("hogehoge@hoge.com");
        assertFalse(accountOptional.isPresent());
    }
}
