package pl.pmerdala.springit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    String login(){
        return "auth/login";
    }

    @GetMapping("/register")
    String register(){
        return "auth/register";
    }

    @GetMapping("/profile")
    String profile(){
        return "auth/profile";
    }
}
