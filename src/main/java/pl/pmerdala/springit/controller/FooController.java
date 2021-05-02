package pl.pmerdala.springit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FooController {

    @GetMapping("/foo")
    String foo(Model model) {
        model.addAttribute("pageTitle", "This page is FOO!");
        return "foo";
    }

}
