package pl.pmerdala.springit.service;

import lombok.extern.slf4j.Slf4j;
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
    private final EmailService emailService;
    private final MapUserRegisterUserData mapUserRegisterUserData;


    public UserService(UserRepository userRepository, RoleService roleService, EmailService emailService, MapUserRegisterUserData mapUserRegisterUserData) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.emailService = emailService;
        this.mapUserRegisterUserData = mapUserRegisterUserData;
    }

    public String getFullNameByLogin(String login) {
        return userRepository.findByLogin(login).map(User::getUserFullName).orElse("Unknow");
    }

    public String getFullNameByCreatedBy(Auditable auditable) {
        return getFullNameByLogin(auditable.getCreatedBy());
    }

    public Optional<User> register(RegisterUserData userData) {
        User user = mapUserRegisterUserData.user(userData);
        user.setActivationCode(UUID.randomUUID().toString());
        user.addRole(roleService.findByName("ROLE_USER"));
        user.setEnabled(false);
        User savedUser = save(user);
        sendActivationEmail(savedUser);
        return Optional.of(savedUser);
    }

    public boolean activate(String email, String activationCode) {
        Optional<User> optionalUser = userRepository.findByEmailAndActivationCode(email, activationCode);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(true);
            user.setActivationCode(null);
            User savedUser = save(user);
            sendWelcomeEmail(savedUser);
            return true;
        }
        return false;
    }

    private void sendActivationEmail(User user) {
        emailService.sendActivationEmail(user);
    }

    private void sendWelcomeEmail(User user) {
        emailService.sendWelcomeEmail(user);
    }

    public Optional<RegisterUserData> registerUserDataForRegistration() {
        return Optional.of(new RegisterUserData());
    }


    private User save(User user) {
        return userRepository.save(user);
    }
}
