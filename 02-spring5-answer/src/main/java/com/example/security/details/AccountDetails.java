package com.example.security.details;

import com.example.persistence.entity.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Accountを保持するUserDetails実装クラス
 */
// TODO 4-12 UserDetailsを実装していることを確認する（変更不要）
public class AccountDetails implements UserDetails {

    private final Account account;
    private final Collection<? extends GrantedAuthority> authorities;

    public AccountDetails(Account account) {
        this.account = account;
        List<String> authorityList = account.getAuthorities();
        this.authorities = AuthorityUtils.createAuthorityList(
                authorityList.toArray(new String[authorityList.size()]));
    }

    public Account getAccount() {
        // TODO 4-13 accountフィールドを返していることを確認する（変更不要）
        return account;
    }

    @Override
    public String getUsername() {
        // TODO 4-14 accountのemailを返す
        return account.getEmail();
    }

    @Override
    public String getPassword() {
        // TODO 4-15 accountのpasswordを返す
        return account.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO 4-16 authoritiesフィールドを返す
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
