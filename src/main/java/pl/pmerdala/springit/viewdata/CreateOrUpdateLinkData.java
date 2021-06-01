package pl.pmerdala.springit.viewdata;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrUpdateLinkData {
    @NonNull
    @NotEmpty(message = "Tytuł nie może być pusty")
    private String title;

    @NonNull
    @NotEmpty(message = "url nie może być pusty")
    @URL(message = "to nie jest URL")
    private String url;

    private String description;
}
