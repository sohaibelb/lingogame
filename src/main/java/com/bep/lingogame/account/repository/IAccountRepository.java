package com.bep.lingogame.account.repository;

import com.bep.lingogame.account.model.Account;

public interface IAccountRepository {
    Account findByUsername(String username);
    int create(Account account);
}
