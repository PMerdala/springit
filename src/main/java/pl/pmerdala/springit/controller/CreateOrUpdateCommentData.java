package pl.pmerdala.springit.controller;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class CreateOrUpdateCommentData {
    @NonNull
    @NotEmpty(message = "komentarz nie może być pusty")
    String body;
}
