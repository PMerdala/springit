package pl.pmerdala.springit.viewdata;

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
    private Long id;
    private String body;
    private LocalDateTime creationDate;
    private String formatCreationDate;
    private ViewLinkData link;
    private String createdBy;
}
