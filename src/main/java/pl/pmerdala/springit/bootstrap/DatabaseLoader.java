package pl.pmerdala.springit.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.pmerdala.springit.config.Constants;
import pl.pmerdala.springit.domain.Link;
import pl.pmerdala.springit.domain.Role;
import pl.pmerdala.springit.domain.User;
import pl.pmerdala.springit.repositories.LinkRepository;
import pl.pmerdala.springit.repositories.RoleRepository;
import pl.pmerdala.springit.repositories.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DatabaseLoader implements CommandLineRunner {
    private final LinkRepository linkRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public DatabaseLoader(LinkRepository linkRepository, RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.linkRepository = linkRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {
        loadRoles();
        loadUsers();
        loadLink();
    }

    private void loadUsers() {
        loadUser("user@gmail.com", "User", "password", new String[]{Constants.ROLE_USER});
        loadUser("admin@gmail.com", "Admin", "password", new String[]{Constants.ROLE_ADMIN});
        loadUser("super@gmail.com", "Super Admin", "password", new String[]{Constants.ROLE_ADMIN, Constants.ROLE_USER});
    }

    private void loadUser(String email, String username, String password, String[] roleNames) {
        if (userRepository.findByLogin(email.toLowerCase(Locale.ROOT)).isPresent()) {
            return;
        }
        List<Role> roles = Arrays.stream(roleNames)
                .map(roleRepository::findByName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toUnmodifiableList());
        String encryptPassword = passwordEncoder.encode(password);
        User user = new User(username, email, encryptPassword, true);
        user.addRoles(roles);
        userRepository.save(user);
    }

    private void loadRoles() {
        String[] rolesToLoad = {Constants.ROLE_USER, Constants.ROLE_ADMIN};
        Arrays.stream(rolesToLoad).forEach(roleName -> {
            if (roleRepository.findByName(roleName).isEmpty()) {
                Role role = new Role(roleName);
                roleRepository.save(role);
            }
        });
    }

    private void loadLink() {
        if (linkRepository.findAll().isEmpty()) {
            Map<String, String> links = new HashMap<>();
            links.put("Securing Spring Boot APIs and SPAs with OAuth 2.0", "https://auth0.com/blog/securing-spring-boot-apis-and-spas-with-oauth2/?utm_source=reddit&utm_medium=sc&utm_campaign=springboot_spa_securing");
            links.put("Easy way to detect Device in Java Web Application using Spring Mobile - Source code to download from GitHub", "https://www.opencodez.com/java/device-detection-using-spring-mobile.htm");
            links.put("Tutorial series about building microservices with SpringBoot (with Netflix OSS)", "https://medium.com/@marcus.eisele/implementing-a-microservice-architecture-with-spring-boot-intro-cdb6ad16806c");
            links.put("Detailed steps to send encrypted email using Java / Spring Boot - Source code to download from GitHub", "https://www.opencodez.com/java/send-encrypted-email-using-java.htm");
            links.put("Build a Secure Progressive Web App With Spring Boot and React", "https://dzone.com/articles/build-a-secure-progressive-web-app-with-spring-boo");
            links.put("Building Your First Spring Boot Web Application - DZone Java", "https://dzone.com/articles/building-your-first-spring-boot-web-application-ex");
            links.put("Building Microservices with Spring Boot Fat (Uber) Jar", "https://jelastic.com/blog/building-microservices-with-spring-boot-fat-uber-jar/");
            links.put("Spring Cloud GCP 1.0 Released", "https://cloud.google.com/blog/products/gcp/calling-java-developers-spring-cloud-gcp-1-0-is-now-generally-available");
            links.put("Simplest way to Upload and Download Files in Java with Spring Boot - Code to download from Github", "https://www.opencodez.com/uncategorized/file-upload-and-download-in-java-spring-boot.htm");
            links.put("Add Social Login to Your Spring Boot 2.0 app", "https://developer.okta.com/blog/2018/07/24/social-spring-boot");
            links.put("File download example using Spring REST Controller", "https://www.jeejava.com/file-download-example-using-spring-rest-controller/");

            links.entrySet().stream()
                    .map(linkData -> new Link(linkData.getKey(), linkData.getValue()))
                    .forEach(linkRepository::save);
        }

        log.info(String.format("Number of links in database %d", linkRepository.count()));
    }
}
