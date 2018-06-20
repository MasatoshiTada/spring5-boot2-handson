package com.example.security.details;

import com.example.persistence.entity.Account;
import com.example.persistence.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

// TODO 4-17 ビジネスロジッククラスであることを示すアノテーションを付加する
@Service
// TODO 4-18 UserDetailsServiceインタフェースを実装していることを確認する（変更不要）
public class AccountDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    // AccountRepositoryをDIする（@Autowired不要）
    public AccountDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // loadUserByUsername()をオーバーライド
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> accountOptional = accountRepository.findByEmail(username);
        Account account = accountOptional.orElseThrow(() -> new UsernameNotFoundException("user not found"));
        // TODO 4-19 AccountDetailsをnewしてreturnする（コンストラクタにAccountを渡す）
        AccountDetails accountDetails = new AccountDetails(account);
        return accountDetails;
    }
}
