package pl.pmerdala.springit.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.pmerdala.springit.domain.Auditable;
import pl.pmerdala.springit.domain.User;
import pl.pmerdala.springit.repositories.UserRepository;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getFullNameByLogin(String login) {
        return userRepository.findByLogin(login).map(User::getUserFullName).orElse("Unknow");
    }

    public String getFullNameByCreatedBy(Auditable auditable) {
        return getFullNameByLogin(auditable.getCreatedBy());
    }
}
