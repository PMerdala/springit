package pl.pmerdala.springit.map;

import org.springframework.stereotype.Component;
import pl.pmerdala.springit.domain.User;
import pl.pmerdala.springit.viewdata.RegisterUserData;

@Component
public class MapUserRegisterUserData {

    public User user(RegisterUserData registerUserData) {
        return new User(registerUserData.getUserFullName(),
                registerUserData.getEmail(),
                registerUserData.getPassword(), false);
    }
}
