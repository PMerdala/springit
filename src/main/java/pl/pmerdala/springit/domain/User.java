package pl.pmerdala.springit.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class User extends Auditable implements UserDetails {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    @Size(min = 6, max = 100)
    @Column(nullable = false)
    private String userFullName;
    @NonNull
    @Size(min = 6, max = 100)
    @Column(nullable = false, unique = true)
    private String email;
    @NonNull
    @Column(nullable = false)
    private String password;
    private String profileImage;
    private LocalDateTime expiredDate;
    private Boolean accountLocked;
    private LocalDateTime credentialsExpiredDate;
    @Column(nullable = false)
    private Boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return expiredDate == null || expiredDate.isAfter(LocalDateTime.now());
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountLocked == null || !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsExpiredDate == null || credentialsExpiredDate.isAfter(LocalDateTime.now());
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
