package pl.pmerdala.springit.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/")
    String home(){
        return "Hello First Home Controller!";
    }
}
