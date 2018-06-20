package com.example.security.details;

import com.example.persistence.entity.Account;
import com.example.persistence.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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
        AccountDetails accountDetails = new AccountDetails(account);
        return accountDetails;
    }
}
