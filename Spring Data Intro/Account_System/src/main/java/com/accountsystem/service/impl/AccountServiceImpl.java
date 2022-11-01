package com.accountsystem.service.impl;

import com.accountsystem.dao.AccountRepository;
import com.accountsystem.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void withdrawMoney(BigDecimal money, Long id) {
        var account = accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Account with the give id does not exist."));
        var newValue = account.getBalance().subtract(money);
        if (newValue.doubleValue() < 0) {
            throw new IllegalArgumentException("Not enough money!");
        }
        account.setBalance(newValue);
        accountRepository.save(account);
    }

    @Override
    public void transferMoney(BigDecimal money, Long id) {
        var account = accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Account with the give id does not exist."));
        var newValue = account.getBalance().add(money);
        account.setBalance(newValue);
        accountRepository.save(account);
    }
}
