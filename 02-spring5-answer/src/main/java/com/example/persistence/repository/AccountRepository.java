package com.example.persistence.repository;

import com.example.persistence.entity.Account;

import java.util.Optional;

public interface AccountRepository {

    Optional<Account> findByEmail(String email);
}
