package pl.pmerdala.springit.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.pmerdala.springit.domain.User;

import java.util.Optional;

@Slf4j
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {

        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .map(o -> {
                    if (o instanceof  User) {
                        return ((User) o).getLogin();
                    }
                    return o.toString();
                }).or(() -> Optional.of("super@gmail.com"));
    }
}
