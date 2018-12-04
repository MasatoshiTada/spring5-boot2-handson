package com.example.web.controller;

import com.example.persistence.config.DataSourceConfig;
import com.example.persistence.config.JdbcConfig;
import com.example.persistence.entity.Customer;
import com.example.service.config.ServiceConfig;
import com.example.web.config.MvcConfig;
import com.example.web.form.CustomerForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

// TODO 3-23 このテストを実行して、CustomerControllerの実装が正しいかチェックする（グリーンになればOK）
@SpringJUnitWebConfig(classes = {DataSourceConfig.class, JdbcConfig.class,
        ServiceConfig.class, MvcConfig.class})
public class CustomerControllerTest {

    @Autowired
    CustomerController customerController;

    JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    @DisplayName("全顧客を検索してindex画面に遷移する")
    public void indexTest() {
        Method indexMethod = assertDoesNotThrow(() -> CustomerController.class.getMethod("index", Model.class));
        GetMapping getMapping = indexMethod.getAnnotation(GetMapping.class);
        ExtendedModelMap model = new ExtendedModelMap();
        String logicalViewName = customerController.index(model);
        Object customersObj = model.get("customers");
        assertAll(
                () -> assertNotNull(getMapping),
                () -> assertEquals("/", getMapping.value()[0]),
                () -> assertEquals("index", logicalViewName),
                () -> assertNotNull(customersObj),
                () -> assertTrue(customersObj instanceof List)
        );
        List<Customer> customers = (List<Customer>) customersObj;
        assertEquals(5, customers.size());
    }

    @Test
    @DisplayName("insertMain画面に遷移する")
    public void insertMainTest() {
        Method indexMethod = assertDoesNotThrow(() -> CustomerController.class.getMethod("insertMain", Model.class));
        GetMapping getMapping = indexMethod.getAnnotation(GetMapping.class);
        ExtendedModelMap model = new ExtendedModelMap();
        String logicalViewName = customerController.insertMain(model);
        Object customerFormObj = model.get("customerForm");
        assertAll(
                () -> assertNotNull(getMapping),
                () -> assertEquals("/insertMain", getMapping.value()[0]),
                () -> assertEquals("insertMain", logicalViewName),
                () -> assertNotNull(customerFormObj),
                () -> assertTrue(customerFormObj instanceof CustomerForm)
        );
        CustomerForm customerForm = (CustomerForm) customerFormObj;
        assertAll(
                () -> assertNull(customerForm.getFirstName()),
                () -> assertNull(customerForm.getLastName()),
                () -> assertNull(customerForm.getEmail()),
                () -> assertNull(customerForm.getBirthday())
        );
    }

    @Test
    @DisplayName("検証エラー時にinsertMain画面に戻る")
    public void insertCompleteTest1() {
        Method indexMethod = assertDoesNotThrow(() -> CustomerController.class.getMethod("insertComplete", CustomerForm.class, BindingResult.class));
        PostMapping postMapping = indexMethod.getAnnotation(PostMapping.class);
        BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(CustomerForm.createEmptyForm(), "customerForm") {
                    @Override
                    public boolean hasErrors() {
                        return true;
                    }
                };
        String logicalViewName = customerController.insertComplete(CustomerForm.createEmptyForm(), bindingResult);
        assertAll(
                () -> assertNotNull(postMapping),
                () -> assertEquals("/insertComplete", postMapping.value()[0]),
                () -> assertEquals("insertMain", logicalViewName)
        );
    }

    @Test
    @DisplayName("顧客の追加ができてindex画面にリダイレクトする")
    @Transactional // テスト終了後にロールバックする
    public void insertCompleteTest2() {
        CustomerForm customerForm = new CustomerForm("絵梨花", "生田",
                "eikuta@nogi.com", LocalDate.of(1997, 1, 22));
        BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(customerForm, "customerForm");
        String logicalViewName = customerController.insertComplete(customerForm, bindingResult);
        int rows = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "customer",
                "email = '" + customerForm.getEmail() + "'");
        assertAll(
                () -> assertEquals(1, rows),
                () -> assertEquals("redirect:/", logicalViewName)
        );
    }
}
