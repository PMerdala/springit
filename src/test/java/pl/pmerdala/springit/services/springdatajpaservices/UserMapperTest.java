package pl.pmerdala.springit.services.springdatajpaservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pmerdala.springit.datamodel.UserSchema;
import pl.pmerdala.springit.model.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    public static final long ID = 1L;
    public static final String USERNAME = "Username";
    public static final String EMAIL = "Email@domena.com";
    public static final String PASSWORD = "Password";
    private UserMapper sut;

    @BeforeEach
    void setUp() {
        sut = new UserMapper();
    }

    @Test
    void mapNull() {
        assertTrue(sut.map(Optional.empty()).isEmpty());
    }

    @Test
    void map() {
        UserSchema userSchema = new UserSchema(ID,USERNAME,EMAIL,PASSWORD);
        Optional<User> user = sut.map(Optional.of(userSchema));
        assertTrue(user.isPresent());
        assertEquals(ID, user.get().getId());
        assertEquals(USERNAME, user.get().getUsername());
        assertEquals(EMAIL, user.get().getEmail());
        assertEquals(PASSWORD,user.get().getPassword());

    }

    @Test
    void reversMapNull() {
        assertTrue(sut.reversMap(Optional.empty()).isEmpty());
    }

    @Test
    void reversMap() {
        User user = new User(ID, USERNAME,EMAIL,PASSWORD);
        Optional<UserSchema> userSchema = sut.reversMap(Optional.of(user));
        assertTrue(userSchema.isPresent());
        assertEquals(ID, userSchema.get().getId());
        assertEquals(USERNAME, userSchema.get().getUsername());
        assertEquals(EMAIL, userSchema.get().getEmail());
        assertEquals(PASSWORD, userSchema.get().getPassword());
    }


}