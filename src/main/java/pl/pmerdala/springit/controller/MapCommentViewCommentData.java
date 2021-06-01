package pl.pmerdala.springit.controller;

import org.springframework.stereotype.Component;
import pl.pmerdala.springit.domain.Comment;
import pl.pmerdala.springit.service.DateTimeFormatter;
import pl.pmerdala.springit.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapCommentViewCommentData {

    private final DateTimeFormatter dateTimeFormatter;
    private final UserService userService;

    public MapCommentViewCommentData(DateTimeFormatter dateTimeFormatter, UserService userService) {
        this.dateTimeFormatter = dateTimeFormatter;
        this.userService = userService;
    }


    ViewCommentData viewCommentData(Comment comment, ViewLinkData link) {
        return ViewCommentData.builder()
                .id(comment.getId())
                .body(comment.getBody())
                .creationDate(comment.getCreatedDate())
                .formatCreationDate(dateTimeFormatter.format(comment.getCreatedDate()))
                .createdBy(userService.getFullNameByCreatedBy(comment))
                .link(link)
                .build();
    }

    List<ViewCommentData> viewCommentDataList(List<Comment> comments, ViewLinkData linkData) {
        return comments.stream()
                .map(comment -> viewCommentData(comment, linkData))
                .collect(Collectors.toUnmodifiableList());
    }
}
