package pl.pmerdala.springit.viewdata;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class RegisterUserData {
    @NonNull
    private String userFullName;
    @NonNull
    @Size(min = 6, max = 100)
    @Email
    private String email;
    @NonNull
    private String password;
}
