package com.example.service;

import com.example.persistence.config.DataSourceConfig;
import com.example.persistence.config.JdbcConfig;
import com.example.persistence.entity.Customer;
import com.example.service.config.ServiceConfig;
import com.example.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = {DataSourceConfig.class, JdbcConfig.class, ServiceConfig.class})
public class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    @DisplayName("findAll()で顧客が全件（5件）取得できる")
    public void findAllTest() {
        Method findAllMethod = assertDoesNotThrow(
                () -> CustomerServiceImpl.class.getMethod("findAll"));
        Transactional transactional = findAllMethod.getAnnotation(Transactional.class);
        assertAll("Transactional annotation not configured correctly",
                () -> assertNotNull(transactional),
                () -> assertEquals(Propagation.REQUIRED, transactional.propagation()),
                () -> assertTrue(transactional.readOnly())
        );

        Iterable<Customer> customers = customerService.findAll();
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
        Method saveMethod = assertDoesNotThrow(
                () -> CustomerServiceImpl.class.getMethod("save", Customer.class));
        Transactional transactional = saveMethod.getAnnotation(Transactional.class);
        assertAll("Transactional annotation not configured correctly",
                () -> assertNotNull(transactional),
                () -> assertEquals(Propagation.REQUIRED, transactional.propagation()),
                () -> assertFalse(transactional.readOnly())
        );

        Customer newCustomer = new Customer("佑唯", "今泉",
                "yimaizumi@keyaki.com", LocalDate.of(1998, 9, 30));
        customerService.save(newCustomer);
        assertEquals(new Integer(6), newCustomer.getId());
        assertEquals(6, JdbcTestUtils.countRowsInTable(jdbcTemplate, "customer"));

        Map<String, Object> map = jdbcTemplate.queryForMap(
                "SELECT id, first_name, last_name, email, birthday" +
                        " FROM customer WHERE id = ?", 6);

        assertAll(
                () -> assertEquals(newCustomer.getId(), map.get("id")),
                () -> assertEquals(newCustomer.getFirstName(), map.get("first_name")),
                () -> assertEquals(newCustomer.getLastName(), map.get("last_name")),
                () -> assertEquals(newCustomer.getEmail(), map.get("email")),
                () -> assertEquals(newCustomer.getBirthday(), ((Date) map.get("birthday")).toLocalDate())
        );
    }

}
