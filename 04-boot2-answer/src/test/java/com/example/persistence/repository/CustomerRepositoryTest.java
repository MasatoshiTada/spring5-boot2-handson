package com.example.persistence.repository;

import com.example.persistence.config.JdbcConfig;
import com.example.persistence.entity.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = JdbcConfig.class)
@JdbcTest
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    @DisplayName("first_nameに「友」が含まれる顧客が全て取得できる")
    public void findByFirstNameOrLastNameTest1() {
        List<Customer> customers = customerRepository.findByFirstNameOrLastName("%友%");
        assertEquals(2, customers.size());
        Customer customer0 = customers.get(0);
        Customer customer1 = customers.get(1);
        assertAll(
                () -> assertEquals(new Integer(1), customer0.getId()),
                () -> assertEquals("友梨奈", customer0.getFirstName()),
                () -> assertEquals("平手", customer0.getLastName()),
                () -> assertEquals("yhirate@keyaki.com", customer0.getEmail()),
                () -> assertEquals(LocalDate.of(2001, 6, 25), customer0.getBirthday()),
                () -> assertEquals(new Integer(3), customer1.getId()),
                () -> assertEquals("友香", customer1.getFirstName()),
                () -> assertEquals("菅井", customer1.getLastName()),
                () -> assertEquals("ysugai@keyaki.com", customer1.getEmail()),
                () -> assertEquals(LocalDate.of(1995, 11, 29), customer1.getBirthday())
        );
    }

    @Test
    @DisplayName("last_nameに「井」が含まれる顧客が全て取得できる")
    public void findByFirstNameOrLastNameTest2() {
        List<Customer> customers = customerRepository.findByFirstNameOrLastName("%井%");
        assertEquals(2, customers.size());
        Customer customer0 = customers.get(0);
        Customer customer1 = customers.get(1);
        assertAll(
                () -> assertEquals(new Integer(3), customer0.getId()),
                () -> assertEquals("友香", customer0.getFirstName()),
                () -> assertEquals("菅井", customer0.getLastName()),
                () -> assertEquals("ysugai@keyaki.com", customer0.getEmail()),
                () -> assertEquals(LocalDate.of(1995, 11, 29), customer0.getBirthday()),
                () -> assertEquals(new Integer(5), customer1.getId()),
                () -> assertEquals("玲香", customer1.getFirstName()),
                () -> assertEquals("桜井", customer1.getLastName()),
                () -> assertEquals("rsakurai@nogi.com", customer1.getEmail()),
                () -> assertEquals(LocalDate.of(1994, 5, 16), customer1.getBirthday())
        );
    }
}
