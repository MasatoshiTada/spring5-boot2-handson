package com.example.persistence.repository;

import com.example.persistence.entity.Account;

import java.util.Optional;

// TODO 4-09 Accountを取得するリポジトリインタフェースを確認する（変更不要）
public interface AccountRepository {

    /**
     * Eメールアドレスに該当するユーザー情報を返します。
     * @param email ユーザーのEメールアドレス
     * @return Eメールアドレスに該当するユーザー情報のOptional。該当するものがない場合は空のOptional。
     */
    Optional<Account> findByEmail(String email);
}
