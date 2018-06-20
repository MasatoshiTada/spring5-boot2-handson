package com.example.persistence.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * DBのaccountテーブルに対応したエンティティクラス
 */
public class Account {

    private Integer id;

    private String name;

    private String email;

    private String password;

    /** Authorityのリスト */
    private List<String> authorities = new ArrayList<>();

    public Account() {}

    public Account(Integer id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void addAuthority(String authority) {
        authorities.add(authority);
    }
}
