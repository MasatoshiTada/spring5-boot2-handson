package com.example.persistence.repository;

import com.example.persistence.entity.Account;

import java.util.Optional;

// TODO 4-09 Accountを取得するリポジトリインタフェースを確認する（変更不要）
public interface AccountRepository {

    Optional<Account> findByEmail(String email);
}
