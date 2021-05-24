package pl.pmerdala.springit.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Vote extends Auditable{
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @ManyToOne
    private Link link;

    @NonNull
    private short direction;

    @NonNull
    private boolean enable = true;
}
