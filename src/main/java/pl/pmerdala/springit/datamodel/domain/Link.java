package pl.pmerdala.springit.datamodel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Link {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String url;
}
