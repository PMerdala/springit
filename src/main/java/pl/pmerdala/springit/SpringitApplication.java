package pl.pmerdala.springit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import pl.pmerdala.springit.config.SpringitProperties;
import pl.pmerdala.springit.domain.Comment;
import pl.pmerdala.springit.domain.Link;
import pl.pmerdala.springit.repositories.CommentRepository;
import pl.pmerdala.springit.repositories.LinkRepository;

@SpringBootApplication
@EnableConfigurationProperties(SpringitProperties.class)
@EnableJpaAuditing
@Slf4j
public class SpringitApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringitApplication.class, args);
    }

}
