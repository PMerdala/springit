package pl.pmerdala.springit.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewCommentData {
    Long id;
    String body;
    LocalDateTime creationDate;
    String formatCreationDate;
    ViewLinkData link;
}
