package pl.pmerdala.springit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    String home(Model model) {
        model.addAttribute("title", "Hello, Thymeleaf!");
        return "home";
    }
}

