package pl.pmerdala.springit.controller;

import org.springframework.stereotype.Component;
import pl.pmerdala.springit.domain.User;
import pl.pmerdala.springit.repositories.UserRepository;

@Component
public class UserHelper {

    private final UserRepository userRepository;

    public UserHelper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getFullNameByLogin(String login) {
        return userRepository.findByLogin(login).map(User::getUserFullName).orElse("Unknow");
    }
}
