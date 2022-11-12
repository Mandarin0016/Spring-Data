package com.example.gamestore.domain.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String email;
    private String password;

    public LoginDTO(String[] args) {
        this.email = args[1];
        this.password = args[2];
    }
}
