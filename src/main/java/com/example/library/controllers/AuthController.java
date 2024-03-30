package com.example.library.controllers;


import com.example.library.models.DTOs.UserLoginDTO;
import com.example.library.models.DTOs.UserRegistrationDTO;
import com.example.library.services.EmailService;
import com.example.library.services.EmailServiceImpl;
import com.example.library.services.UserServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class AuthController {
    private final UserServiceImpl userService;



    @Autowired
    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @GetMapping("/register")
    public String getRegistrationForm(Model model) {

        if (!model.containsAttribute("userRegistrationDTO")) {
            model.addAttribute("userRegistrationDTO", new UserRegistrationDTO());
        }

        return "auth-register";
    }


    @PostMapping("/register/save")
    public String registration(@Valid UserRegistrationDTO userRegistrationDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegistrationDTO", userRegistrationDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationDTO", bindingResult);
            redirectAttributes.addFlashAttribute("badCredentials", true);
            return "redirect:/users/register";
        }

        if (userService.isEmailExists(userRegistrationDTO.getEmail())) {
            model.addAttribute("errorMessage", "User with this email already exists!");
            return "auth-register";
        }


        if (!this.userService.registerUser(userRegistrationDTO)) {
            model.addAttribute("errorMessage", "Passwords must be equals!");
            return "auth-register";
        }

        return "redirect:/users/login";
    }


    @ModelAttribute("badCredentials")
    public boolean initBadCredentials() {
        return false;
    }


    @GetMapping("/login")
    public String showLoginForm(Model model) {

        if (!model.containsAttribute("userLoginDTO")) {
            model.addAttribute("userLoginDTO", new UserLoginDTO());
        }

        return "/auth-login";
    }


    @PostMapping("/login")
    public String login(UserLoginDTO userLoginDTO, RedirectAttributes redirectAttributes) {

        if (!this.userService.loginUser(userLoginDTO)) {
            redirectAttributes.addFlashAttribute("userLoginDTO", userLoginDTO);
            redirectAttributes.addFlashAttribute("badCredentials", true);

            return "redirect:/users/login";
        }

        return "redirect:/";
    }




    @GetMapping("/reset-password")
    public String resetPasswordPage() {
        return "forgot-password";
    }



    @PostMapping("/reset-password")
    public String sendResetPasswordCode(@RequestParam("email") String email,  RedirectAttributes redirectAttributes) {
        if (email.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid email address!");
            return "redirect:/users/reset-password";
        }

         userService.requestPasswordReset();
         userService.sendResetPasswordCode(email);


        return "redirect:/users/login?password_reset=true";
    }

    @PostMapping("/logout")
    public String logout() {
        this.userService.logOutUser();

        return "redirect:/";
    }
}
