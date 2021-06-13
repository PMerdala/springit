package pl.pmerdala.springit.viewdata;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.pmerdala.springit.viewdata.validator.PasswordsMatch;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@PasswordsMatch
public class RegisterUserData {
    @NonNull
    @NotEmpty(message = "Imię musi być podane")
    private String firstName;
    @NonNull
    @NotEmpty(message = "Nazwisko musi być podane")
    private String lastName;
    @NonNull
    @NotEmpty(message = "Alias musi być podany")
    private String alias;
    @NonNull
    @Size(min = 6, max = 100)
    @NotEmpty(message = "email musi być podany")
    @Email
    private String email;
    @NonNull
    @NotEmpty(message = "hasło musi być podane")
    @Size(min = 6, max = 100)
    private String password;
    @NonNull
    @NotEmpty(message = "potwierdzenie hasła musi być podane")
    @Size(min = 6, max = 100)
    private String confirmPassword;
}
