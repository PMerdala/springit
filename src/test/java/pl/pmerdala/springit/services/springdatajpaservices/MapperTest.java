package pl.pmerdala.springit.services.springdatajpaservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MapperTest {

    public static final String TEST = "test";
    private Mapper<String, String> sut;

    @BeforeEach
    void setUp() {
        sut = new Mapper<String, String>() {
            @Override
            public Optional<String> map(Optional<String> source) {
                return source;
            }

            @Override
            public Optional<String> reversMap(Optional<String> source) {
                return source;
            }
        };
    }

    @Test
    void mapNull() {
        String string = null;
        assertNull(sut.map(string));
    }

    @Test
    void map() {
        String string = TEST;
        assertEquals(TEST, sut.map(string));
    }

    @Test
    void reversMapNull() {
        String string = null;
        assertNull(sut.reversMap(string));
    }

    @Test
    void reversMap() {
        String string = TEST;
        assertEquals(TEST, sut.reversMap(string));
    }
}
