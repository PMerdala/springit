package pl.pmerdala.springit.controller;

import org.springframework.stereotype.Component;
import pl.pmerdala.springit.domain.Comment;
import pl.pmerdala.springit.domain.Link;
import pl.pmerdala.springit.domain.User;
import pl.pmerdala.springit.repositories.UserRepository;
import pl.pmerdala.springit.service.DateTimeFormatter;

import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapLinkViewLinkData {
    private final MapCommentViewCommentData mapCommentViewCommentData;
    private final DateTimeFormatter dateTimeFormatter;
    private final UserRepository userRepository;

    public MapLinkViewLinkData(MapCommentViewCommentData mapCommentViewCommentData, DateTimeFormatter dateTimeFormatter, UserRepository userRepository) {
        this.mapCommentViewCommentData = mapCommentViewCommentData;
        this.dateTimeFormatter = dateTimeFormatter;
        this.userRepository = userRepository;
    }

    ViewLinkData viewLinkData(Link link) {
        ViewLinkData linkData = ViewLinkData.builder()
                .id(link.getId())
                .title(link.getTitle())
                .url(link.getUrl())
                .domainName(getDomainName(link))
                .numberOfComments(link.getComments().size())
                .formatCreationDate(dateTimeFormatter.format(link.getCreatedDate()))
                .creationDate(link.getCreatedDate())
                .createdBy(getUserFullName(link))
                .vote(link.getVoteCount())
                .build();
        linkData.setComments(viewCommentDataList(link.getComments(), linkData));
        return linkData;
    }

    private String getUserFullName(Link link) {
        return userRepository.findByLogin(link.getCreatedBy()).map(User::getUserFullName).orElse("Unknown");
    }

    Link link(CreateOrUpdateLinkData data) {
        return new Link(data.getTitle(), data.getUrl(), data.getDescription());
    }

    void updateLink(CreateOrUpdateLinkData data, Link link) {
        link.setTitle(data.getTitle());
        link.setUrl(data.getUrl());
        link.setDescription(data.getDescription());
    }

    CreateOrUpdateLinkData createOrUpdateLinkData(Link link) {
        return CreateOrUpdateLinkData.builder()
                .title(link.getTitle())
                .url(link.getUrl())
                .description(link.getDescription())
                .build();
    }

    List<ViewLinkData> viewLinkDataList(List<Link> links) {
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