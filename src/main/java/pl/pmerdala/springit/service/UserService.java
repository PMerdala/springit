package pl.pmerdala.springit.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.pmerdala.springit.domain.Auditable;
import pl.pmerdala.springit.domain.User;
import pl.pmerdala.springit.map.MapUserRegisterUserData;
import pl.pmerdala.springit.repositories.UserRepository;
import pl.pmerdala.springit.viewdata.RegisterUserData;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final MapUserRegisterUserData mapUserRegisterUserData;

    public UserService(UserRepository userRepository, MapUserRegisterUserData mapUserRegisterUserData) {
        this.userRepository = userRepository;
        this.mapUserRegisterUserData = mapUserRegisterUserData;
    }

    public String getFullNameByLogin(String login) {
        return userRepository.findByLogin(login).map(User::getUserFullName).orElse("Unknow");
    }

    public String getFullNameByCreatedBy(Auditable auditable) {
        return getFullNameByLogin(auditable.getCreatedBy());
    }

    public User register(RegisterUserData userData) {
        return mapUserRegisterUserData.user(userData);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
