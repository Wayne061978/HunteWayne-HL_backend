package com.healthlink.huntewaynehl_backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied"; // ✅ Ensure this matches the Thymeleaf file name
    }
}

