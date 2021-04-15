package pl.pmerdala.springit.datamodel;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class CommentSchema {

    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String comment;
    ///private LinkSchema link;
}
