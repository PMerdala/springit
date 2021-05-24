package pl.pmerdala.springit.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewLinkData {
    private Long id;
    private String title;
    private String url;
    private String domainName;
    private String formatCreationDate;
    private LocalDateTime creationDate;
    private String createdBy;
    private Integer numberOfComments;
    private List<ViewCommentData> comments;
    private int vote;
}
