package pl.pmerdala.springit.viewdata.validator;

import pl.pmerdala.springit.viewdata.RegisterUserData;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, RegisterUserData> {
    @Override
    public boolean isValid(RegisterUserData userData, ConstraintValidatorContext constraintValidatorContext) {
        return userData != null && userData.getPassword()!=null && userData.getPassword().equals(userData.getConfirmPassword());
    }
}
