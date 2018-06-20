package com.example.service;

import com.example.persistence.entity.Customer;

// TODO 2-01 ビジネスロジックインタフェースの内容を確認する（変更不要）
public interface CustomerService {

    /**
     * 顧客を全件検索する
     */
    Iterable<Customer> findAll();

    /**
     * 1件の顧客をDBに追加する
     * @param customer 追加する顧客
     */
    void save(Customer customer);
}
