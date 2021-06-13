package pl.pmerdala.springit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.pmerdala.springit.domain.User;
import pl.pmerdala.springit.service.UserService;
import pl.pmerdala.springit.viewdata.RegisterUserData;

import javax.validation.Valid;
import java.util.Optional;

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
        model.addAttribute("registerUserData", userService.registerUserDataForRegistration().orElseThrow());
        model.addAttribute("success",model.containsAttribute("success"));
        return "auth/register";
    }

    @PostMapping("/register")
    String registerUser(@Valid @ModelAttribute("registerUserData") RegisterUserData userData,
                        BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("validationErrors",bindingResult.getAllErrors());
            model.addAttribute("registerUserData",userData);
            return "auth/register";
        }
        Optional<User> optionalUser = userService.register(userData);
        optionalUser.ifPresent(user -> {
            redirectAttributes.addAttribute("id",user.getId())
                    .addFlashAttribute("success",true);
        });
        return "redirect:/register";
    }

    @GetMapping("/profile")
    String profile() {
        return "auth/profile";
    }

    @GetMapping("/activate/{userEmail}/{activationCode}")
    String activate(@PathVariable("userEmail") String userEmail, @PathVariable("activationCode")String activationCode){
        boolean activated = userService.activate(userEmail,activationCode);
        if (activated){
            return "auth/activated";
        }
        return "redirect:/";
    }
}
