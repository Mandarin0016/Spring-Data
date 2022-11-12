package com.example.gamestore.service;

import com.example.gamestore.domain.dto.LoginDTO;
import com.example.gamestore.domain.dto.RegisterDTO;
import com.example.gamestore.domain.entity.User;

public interface UserService {
    User register(RegisterDTO registerData);

    User login(LoginDTO loginData);

    String logout();

    User getLoggedInUser();
}
