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
import java.util.Locale;
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
    @Column(nullable = false)
    private String firstName;
    @NonNull
    @Column(nullable = false)
    private String lastName;
    @NonNull
    @Column(nullable = false)
    private String alias;
    @NonNull
    @Size(min = 6, max = 100)
    @Column(nullable = false, unique = true)
    private String email;
    @Size(min = 6, max = 100)
    @Column(nullable = false, unique = true)
    private String login;
    @NonNull
    @Column(nullable = false)
    private String password;
    private String profileImage;
    private LocalDateTime expiredDate;
    private Boolean accountLocked;
    private LocalDateTime credentialsExpiredDate;
    private String activationCode;

    @NonNull
    @Column(nullable = false)
    private Boolean enabled;

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

    public String getUserFullName() {
        return firstName+ " " + lastName;
    }

    public void addRoles(Collection<Role> newRoles) {
        roles.addAll(newRoles);
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    @PrePersist
    @PreUpdate
    private void prepare() {
        login = email.toLowerCase(Locale.ROOT);
    }

    @Override
    public String toString(){
        return String.format("%s (%d)", login,id);
    }
}
