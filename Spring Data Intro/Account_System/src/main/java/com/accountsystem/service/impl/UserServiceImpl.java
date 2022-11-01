package com.accountsystem.service.impl;

import com.accountsystem.dao.AccountRepository;
import com.accountsystem.dao.UserRepository;
import com.accountsystem.model.Account;
import com.accountsystem.model.User;
import com.accountsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void register(String username, int age, BigDecimal initialAmount) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("User with the giver 'username' already exist.");
        }
        User user = new User();
        user.setUsername(username);
        user.setAge(age);
        userRepository.save(user);

        Account account = new Account();
        account.setBalance(initialAmount);
        account.setUser(user);
        accountRepository.save(account);
    }
}
