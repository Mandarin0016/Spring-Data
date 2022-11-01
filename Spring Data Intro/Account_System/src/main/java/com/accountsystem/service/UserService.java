package com.accountsystem.service;

import java.math.BigDecimal;

public interface UserService {
    void register(String username, int age, BigDecimal initialAmount);
}
