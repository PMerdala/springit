package pl.pmerdala.springit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.pmerdala.springit.service.UserService;
import pl.pmerdala.springit.viewdata.RegisterUserData;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    String register(Model model) {
        model.addAttribute("registerUserData", new RegisterUserData());
        return "auth/register";
    }

    @GetMapping("/profile")
    String profile(){
        return "auth/profile";
    }
}
