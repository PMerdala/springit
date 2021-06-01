package pl.pmerdala.springit.map;

import org.springframework.stereotype.Component;
import pl.pmerdala.springit.domain.Comment;
import pl.pmerdala.springit.domain.Link;
import pl.pmerdala.springit.service.DateTimeFormatter;
import pl.pmerdala.springit.service.UserService;
import pl.pmerdala.springit.viewdata.CreateOrUpdateLinkData;
import pl.pmerdala.springit.viewdata.ViewCommentData;
import pl.pmerdala.springit.viewdata.ViewLinkData;

import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapLinkViewLinkData {
    private final MapCommentViewCommentData mapCommentViewCommentData;
    private final DateTimeFormatter dateTimeFormatter;
    private final UserService userService;

    public MapLinkViewLinkData(MapCommentViewCommentData mapCommentViewCommentData,
                               DateTimeFormatter dateTimeFormatter,
                               UserService userService) {
        this.mapCommentViewCommentData = mapCommentViewCommentData;
        this.dateTimeFormatter = dateTimeFormatter;
        this.userService = userService;
    }

    public ViewLinkData viewLinkData(Link link) {
        ViewLinkData linkData = ViewLinkData.builder()
                .id(link.getId())
                .title(link.getTitle())
                .url(link.getUrl())
                .domainName(getDomainName(link))
                .numberOfComments(link.getComments().size())
                .formatCreationDate(dateTimeFormatter.format(link.getCreatedDate()))
                .creationDate(link.getCreatedDate())
                .createdBy(userService.getFullNameByCreatedBy(link))
                .vote(link.getVoteCount())
                .build();
        linkData.setComments(viewCommentDataList(link.getComments(), linkData));
        return linkData;
    }

    public Link link(CreateOrUpdateLinkData data) {
        return new Link(data.getTitle(), data.getUrl(), data.getDescription());
    }

    public void updateLink(CreateOrUpdateLinkData data, Link link) {
        link.setTitle(data.getTitle());
        link.setUrl(data.getUrl());
        link.setDescription(data.getDescription());
    }

    public CreateOrUpdateLinkData createOrUpdateLinkData(Link link) {
        return CreateOrUpdateLinkData.builder()
                .title(link.getTitle())
                .url(link.getUrl())
                .description(link.getDescription())
                .build();
    }

    public List<ViewLinkData> viewLinkDataList(List<Link> links) {
        return links.stream().map(this::viewLinkData).collect(Collectors.toUnmodifiableList());
    }

    private String getDomainName(Link link) {
        try {
            return link.getDomainName();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return "";
    }

    private List<ViewCommentData> viewCommentDataList(List<Comment> comments, ViewLinkData linkData) {
        return mapCommentViewCommentData.viewCommentDataList(comments, linkData);
    }
}