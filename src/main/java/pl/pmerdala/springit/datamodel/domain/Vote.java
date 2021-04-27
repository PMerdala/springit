package pl.pmerdala.springit.datamodel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vote {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Link link;
    private int vote;
}
