package pl.pmerdala.springit.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pmerdala.springit.domain.Auditable;
import pl.pmerdala.springit.domain.User;
import pl.pmerdala.springit.map.MapUserRegisterUserData;
import pl.pmerdala.springit.repositories.UserRepository;
import pl.pmerdala.springit.viewdata.RegisterUserData;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final MapUserRegisterUserData mapUserRegisterUserData;
    private final BCryptPasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, RoleService roleService, MapUserRegisterUserData mapUserRegisterUserData, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.mapUserRegisterUserData = mapUserRegisterUserData;
        this.passwordEncoder = passwordEncoder;
    }

    public String getFullNameByLogin(String login) {
        return userRepository.findByLogin(login).map(User::getUserFullName).orElse("Unknow");
    }

    public String getFullNameByCreatedBy(Auditable auditable) {
        return getFullNameByLogin(auditable.getCreatedBy());
    }

    public Optional<User> register(RegisterUserData userData) {
        User user = mapUserRegisterUserData.user(userData);
        String confirmPasswordEncypt = passwordEncoder.encode(userData.getConfirmPassword());
        user.setActivationCode(UUID.randomUUID().toString());
        user.addRole(roleService.findByName("ROLE_USER"));
        user.setEnabled(false);
        User savedUser = save(user);
        sendActivationEmail(savedUser);
        return Optional.of(savedUser);
    }

    private void sendActivationEmail(User savedUser) {
        log.info("Send email to " +savedUser);
    }

    public Optional<RegisterUserData> registerUserDataForRegistration(){
        return Optional.of(new RegisterUserData());
    }


    private User save(User user) {
        return userRepository.save(user);
    }
}
