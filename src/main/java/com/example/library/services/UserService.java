package com.example.library.services;

import com.example.library.models.DTOs.UserLoginDTO;
import com.example.library.models.DTOs.UserRegistrationDTO;
import com.example.library.util.CurrentUser;
import com.example.library.util.UserForm;

public interface UserService {
    Boolean registerUser(UserRegistrationDTO userRegistrationDTO);

    Boolean loginUser(UserLoginDTO userLoginDTO);

    Boolean isEmailExists(String email);

    void logOutUser();

    CurrentUser getCurrentUser();

    void updateUser(UserForm userForm);

    void sendResetPasswordCode(String email);

    void requestPasswordReset();

    void updatePassword(String email, String newPassword);
}
