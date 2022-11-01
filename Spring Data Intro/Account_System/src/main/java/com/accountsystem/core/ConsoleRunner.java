package com.accountsystem.core;

import com.accountsystem.service.AccountService;
import com.accountsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final UserService userService;
    private final AccountService accountService;

    @Autowired
    public ConsoleRunner(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {
        //Register user
        this.userService.register("Pesho", 25, new BigDecimal(1000));

        //Withdraw money from account with ID 1
        this.accountService.withdrawMoney(new BigDecimal(800), 1L);
        //Transfer money into account with ID 1
        this.accountService.transferMoney(new BigDecimal(300), 1L);
    }
}
