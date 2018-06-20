package com.example.persistence.repository;

import com.example.persistence.config.DataSourceConfig;
import com.example.persistence.config.JdbcConfig;
import com.example.persistence.entity.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = {DataSourceConfig.class,
        JdbcConfig.class, CustomerRepositoryTest.TestTransactionConfig.class})
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    @DisplayName("findAll()で顧客が全件（5件）取得できる")
    public void findAllTest() {
        Iterable<Customer> customers = customerRepository.findAll();
        int count = 0;
        for (Customer customer : customers) {
            assertAll(
                    () -> assertNotNull(customer.getId()),
                    () -> assertNotNull(customer.getFirstName()),
                    () -> assertNotNull(customer.getLastName()),
                    () -> assertNotNull(customer.getEmail()),
                    () -> assertNotNull(customer.getBirthday())
            );
            count++;
        }
        assertEquals(5, count);
    }

    @Test
    @DisplayName("save()で顧客が1件追加できる")
    @Transactional // テスト終了後にロールバック
    public void saveTest() {
        Customer newCustomer = new Customer("佑唯", "今泉",
                "yimaizumi@keyaki.com", LocalDate.of(1998, 9, 30));
        customerRepository.save(newCustomer);
        int newCustomerId = 6;
        assertEquals(new Integer(newCustomerId), newCustomer.getId());
        assertEquals(6, JdbcTestUtils.countRowsInTable(jdbcTemplate, "customer"));

        Optional<Customer> customerOptional = customerRepository.findById(newCustomerId);
        assertTrue(customerOptional.isPresent());
        Customer customer = customerOptional.get();
        assertAll(
                () -> assertEquals(newCustomer.getId(), customer.getId()),
                () -> assertEquals(newCustomer.getFirstName(), customer.getFirstName()),
                () -> assertEquals(newCustomer.getLastName(), customer.getLastName()),
                () -> assertEquals(newCustomer.getEmail(), customer.getEmail()),
                () -> assertEquals(newCustomer.getBirthday(), customer.getBirthday())
        );
    }

    @Configuration
    @EnableTransactionManagement
    public static class TestTransactionConfig {

        @Bean
        public PlatformTransactionManager transactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
    }

}
