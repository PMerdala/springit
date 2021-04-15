package pl.pmerdala.springit.services.springdatajpaservices;

import pl.pmerdala.springit.datamodel.UserSchema;
import pl.pmerdala.springit.model.User;

import java.util.Optional;

public class UserMapper implements Mapper<UserSchema, User> {
    @Override
    public Optional<User> map(Optional<UserSchema> source) {
        return source.map(s->new User(s.getId(),s.getUsername(),s.getEmail(),s.getPassword()));
    }

    @Override
    public Optional<UserSchema> reversMap(Optional<User> source) {
        return source.map(s->new UserSchema(s.getId(),s.getUsername(),s.getEmail(),s.getPassword()));
    }
}
