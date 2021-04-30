package pl.pmerdala.springit.commandrunner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Order(2)
public class DatabaseLoader implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("Database Loader ...");
    }
}
