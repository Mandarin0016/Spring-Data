package com.example.gamestore.service.impl;

import com.example.gamestore.domain.dto.LoginDTO;
import com.example.gamestore.domain.dto.RegisterDTO;
import com.example.gamestore.domain.entity.User;
import com.example.gamestore.repository.UserRepository;
import com.example.gamestore.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private User loggedInUser = null;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(RegisterDTO registerData) {
        User user = modelMapper.map(registerData, User.class);
        if (userRepository.count() <= 0) {
            user.setAdmin(true);
        }
        return userRepository.save(user);
    }

    @Override
    public User login(LoginDTO loginData) {
        User user = userRepository.findByEmail(loginData.getEmail()).orElseThrow(() -> new IllegalArgumentException("Incorrect username / password"));
        if (!loginData.getPassword().equals(user.getPassword())) {
            throw new IllegalArgumentException("Incorrect username / password");
        }
        this.loggedInUser = user;
        return user;
    }

    @Override
    public User getLoggedInUser() {
        return this.loggedInUser;
    }

    @Override
    public String logout() {
        if (getLoggedInUser() == null) {
            throw new IllegalArgumentException("Cannot log out. No user was logged in.");
        }
        String output = String.format("User %s successfully logged out%n", loggedInUser.getFullName());
        this.loggedInUser = null;
        return output;
    }
}
