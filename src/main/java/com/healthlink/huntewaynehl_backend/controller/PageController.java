package com.healthlink.huntewaynehl_backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/Contact")
    public String Contact() {
        return "contactUs"; // The Thymeleaf template name, e.g., contactUs.html
    }

    @GetMapping("/aboutUs")
    public String aboutUs() {
        return "aboutUs";


    }
//
//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }
//
//    @GetMapping("/register")
//    public String register() {
//        return "register";
//    }

//    @GetMapping("/signup")
//    public String signup() {
//        return "signup";
//    }

//    @GetMapping("/home")
//    public String home() {
//        return "home";
//    }
}
