package pl.pmerdala.springit.datamodel;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class VoteSchema {
    @Id
    @GeneratedValue
    private Long id;
    //private LinkSchema link;
    @NonNull
    private int vote;
}
