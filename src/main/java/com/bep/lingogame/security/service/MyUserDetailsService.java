package com.bep.lingogame.security.service;

import com.bep.lingogame.account.model.Account;
import com.bep.lingogame.account.service.AccountService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private AccountService accountService;

    public MyUserDetailsService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Account account = accountService.getAccountByUsername(userName);
        return new User(account.getUsername(), account.getPassword(), new ArrayList<>());
    }
}
