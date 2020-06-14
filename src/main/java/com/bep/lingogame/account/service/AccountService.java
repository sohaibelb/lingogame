package com.bep.lingogame.account.service;

import com.bep.lingogame.account.model.Account;
import com.bep.lingogame.account.repository.AccountRepository;
import com.bep.lingogame.account.repository.IAccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final IAccountRepository iAccountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.iAccountRepository = accountRepository;
    }

    public Account getAccountByUsername(String username) {
        return iAccountRepository.findByUsername(username);
    }

    public int createAccount(Account account) {
        if (iAccountRepository.findByUsername(account.getUsername()) != null) {
            return 0;
        }
        return iAccountRepository.create(account);
    }
}
