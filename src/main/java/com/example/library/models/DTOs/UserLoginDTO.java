package com.example.library.models.DTOs;

public class UserLoginDTO {
    private String email;

    private String password;

    private String username;

    public UserLoginDTO() {

    }


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public UserLoginDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserLoginDTO setPassword(String password) {
        this.password = password;
        return this;
    }
}