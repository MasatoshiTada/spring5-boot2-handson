package com.example.persistence.repository.impl;

import com.example.persistence.entity.Account;
import com.example.persistence.repository.AccountRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

/**
 * AccountRepository実装クラス。
 * Spring Data JDBCを使わず、NamedParameterJdbcTemplateを直接利用しています。
 */
@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    // NamedParameterJdbcTemplateをDI（@Autowired不要）
    public AccountRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("email", email);
        String sql = "SELECT acc.id, acc.name, acc.email, acc.password, auth.authority_name FROM account acc" +
                " JOIN account_authority auth ON acc.id = auth.account_id WHERE email = :email";
        Account account = jdbcTemplate.query(sql, params, new AccountExtractor()); // ラムダ式で書くことも可能
        return Optional.ofNullable(account);
    }

    /**
     * ResultSetExtractor実装クラス。
     * ResultSetから取得した値をAccountに変換します。
     */
    private static class AccountExtractor implements ResultSetExtractor<Account> {
        @Override
        public Account extractData(ResultSet rs) throws SQLException, DataAccessException {
            Account account = null;
            while (rs.next()) {
                if (account == null) {
                    account = new Account(rs.getInt("id"), rs.getString("name"),
                            rs.getString("email"), rs.getString("password"));
                }
                account.addAuthority(rs.getString("authority_name"));
            }
            return account;
        }
    }
}
