package com.user_management_app.UserManagement.controller;

import com.user_management_app.UserManagement.dto.CreateUserRequest;
import com.user_management_app.UserManagement.service.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserServices userService;

    @GetMapping("/")
    public String getHomePage(@AuthenticationPrincipal(expression = "username") String username, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) {
            return "redirect:/adminpanel";
        }
        model.addAttribute("username", username);
        return "home";
    }
    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            if (error != null) {
                model.addAttribute("error`", "Invalid username or password");
            }
            return "login";
        }
        return "redirect:/";
    }

    @GetMapping("/signup")
    public String showSignupPage(Model model){
        model.addAttribute("signuprequest",new CreateUserRequest());
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute("signuprequest") CreateUserRequest signUpReq,Model model) {
        boolean result = userService.createUser(signUpReq);
        if (!result && userService.getFlag() == 1) {
            model.addAttribute("error", "Username or email already exists");
            return "/signup";
        } else if (!result && userService.getFlag() == 0) {
            return "/signup";
        } else {
            return "redirect:/login";
        }
    }
}
