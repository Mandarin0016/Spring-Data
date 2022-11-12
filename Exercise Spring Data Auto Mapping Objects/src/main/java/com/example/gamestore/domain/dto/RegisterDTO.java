package com.example.gamestore.domain.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;

    /**
     * @param args all data read from the console
     */
    public RegisterDTO(String[] args) {
        this.email = args[1];
        this.password = args[2];
        this.confirmPassword = args[3];
        this.fullName = args[4];
    }
}
