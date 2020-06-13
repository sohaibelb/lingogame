package com.bep.lingogame.account.service;

import com.bep.lingogame.account.model.Account;
import com.bep.lingogame.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class AccountServiceTest {

    @Mock
    private AccountRepository mockAccountRepository;

    private AccountService accountServiceUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        accountServiceUnderTest = new AccountService(mockAccountRepository);
    }

    @Test
    void testGetAccountByUsername() {
        final Account expectedResult = new Account(0, "username", "password");
        when(mockAccountRepository.findByUsername("username")).thenReturn(new Account(0, "username", "password"));

        final Account result = accountServiceUnderTest.getAccountByUsername("username");

        assertEquals(expectedResult, result);
    }

    @Test
    void testCreateAccount() {
        final Account account = new Account(0, "username", "password");
        when(mockAccountRepository.create(new Account(0, "username", "password"))).thenReturn(0);

        final int result = accountServiceUnderTest.createAccount(account);

        assertEquals(0, result);
    }
}
