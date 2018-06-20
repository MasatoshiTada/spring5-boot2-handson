package com.example.persistence.entity;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;

/**
 * DBのcustomerテーブルに対応したエンティティクラス
 */
public class Customer {

    @Id
    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private LocalDate birthday;

    public Customer() {}

    public Customer(Integer id, String firstName, String lastName, String email, LocalDate birthday) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
    }

    public Customer(String firstName, String lastName, String email, LocalDate birthday) {
        this(null, firstName, lastName, email, birthday);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
