package pl.pmerdala.springit.map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.pmerdala.springit.domain.User;
import pl.pmerdala.springit.viewdata.RegisterUserData;

@Component
public class MapUserRegisterUserData {

    private final BCryptPasswordEncoder passwordEncoder;

    public MapUserRegisterUserData(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User user(RegisterUserData registerUserData) {
        return new User(registerUserData.getFirstName(),
                registerUserData.getLastName(),
                registerUserData.getAlias(),
                registerUserData.getEmail(),
                passwordEncoder.encode(registerUserData.getPassword()), false);
    }
}
