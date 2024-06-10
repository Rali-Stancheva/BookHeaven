package com.example.library.models.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class UserRegistrationDTO {
    @NotNull
    @NotBlank
//    @Length(min = 2, max = 20)
    private String username;

    @NotNull
    @NotBlank
    @Length(min = 6)
    @Email
    private String email;

    @NotNull
    @NotBlank
    @Length(min = 6, max = 20)
    private String password;

    @NotNull
    @NotBlank
    private String confirmPassword;

    public UserRegistrationDTO() {

    }

    public String getEmail() {
        return email;
    }

    public UserRegistrationDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegistrationDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegistrationDTO setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
