package com.example.library.controllers;

import com.example.library.services.UserService;
import com.example.library.util.CurrentUser;
import com.example.library.util.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUserInfo(Model model) {
        CurrentUser currentUser = userService.getCurrentUser();
        UserForm userForm = new UserForm();
        userForm.setUsername(currentUser.getUsername());
        userForm.setEmail(currentUser.getEmail());

        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("email", currentUser.getEmail());
        model.addAttribute("userForm", userForm);
        return "profile";
    }

    @PostMapping("/update")
    public String updateProfile(@ModelAttribute("userForm") UserForm userForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/profile";
        }
        userService.updateUser(userForm);

        model.addAttribute("successMessage", "Success! Your profile information has been updated successfully.");

        userForm.setUsername("");
        userForm.setEmail("");
        userForm.setPassword("");
        userForm.setConfirmPassword("");

       // return "profile";
       return "redirect:/users/login";
    }
}