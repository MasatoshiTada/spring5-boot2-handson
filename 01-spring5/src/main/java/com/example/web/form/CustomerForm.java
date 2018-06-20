package com.example.web.form;

import com.example.persistence.entity.Customer;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class CustomerForm {

    // TODO 3-01 検証アノテーションを付加する（空白不可、長さ1から32まで）


    private String firstName;

    // TODO 3-02 検証アノテーションを付加する（空白不可、長さ1から32まで）


    private String lastName;

    // TODO 3-03 検証アノテーションを付加する（空白不可、長さ1から128まで、Eメール形式）



    private String email;

    // TODO 3-04 検証アノテーションを付加する（null不可）

    // TODO 3-05 アノテーションで日付フォーマットを「yyyy-MM-dd」に指定する

    private LocalDate birthday;

    public CustomerForm(String firstName,
                        String lastName,
                        String email,
                        // TODO 3-06 アノテーションで日付フォーマットを「yyyy-MM-dd」に指定する
                            LocalDate birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
    }

    /**
     * フィールドがすべて空（null）のCustomerFormインスタンスを生成するメソッド
     */
    public static CustomerForm createEmptyForm() {
        return new CustomerForm(null, null, null, null);
    }

    /**
     * エンティティのCustomerに変換するメソッド
     */
    public Customer convertToEntity() {
        return new Customer(firstName, lastName, email, birthday);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }
}
