package pl.pmerdala.springit.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Role extends Auditable {

    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    @Override
    public String toString(){
        return String.format("%s (%d)", name,id) ;
    }
}
