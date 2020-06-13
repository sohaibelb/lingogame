package com.bep.lingogame.controller;

import com.bep.lingogame.account.model.Account;
import com.bep.lingogame.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody Account account) {
        //Create Account
        int accountCreated = accountService.createAccount(account);

        //Checking if account is succesfully created
        if (accountCreated != 0) {
            return new ResponseEntity<>("An account is created", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Could not create an account", HttpStatus.BAD_REQUEST);
        }
    }

}
