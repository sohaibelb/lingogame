package com.bep.lingogame.controller;

import com.bep.lingogame.account.model.Account;
import com.bep.lingogame.account.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class RegistrationControllerTest {

    @Mock
    private AccountService mockAccountService;

    @InjectMocks
    private RegistrationController registrationControllerUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void testRegisterUser() {
        final Account account = new Account(0, "username", "password");
        final ResponseEntity<String> expectedResult = new ResponseEntity<>("An account is created", HttpStatus.OK);
        when(mockAccountService.createAccount(new Account(0, "username", "password"))).thenReturn(1);

        final ResponseEntity<String> result = registrationControllerUnderTest.registerUser(account);

        assertEquals(expectedResult, result);
    }
}
