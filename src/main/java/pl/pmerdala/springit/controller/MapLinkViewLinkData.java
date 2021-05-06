package pl.pmerdala.springit.controller;

import org.springframework.stereotype.Component;
import pl.pmerdala.springit.domain.Link;
import pl.pmerdala.springit.domain.User;
import pl.pmerdala.springit.repositories.UserRepository;
import pl.pmerdala.springit.service.DateTimeFormatter;

import java.net.URISyntaxException;
import java.util.List;

@Component
public class MapLinkViewLinkData extends AbstractMap<Link, ViewLinkData> {
    private final MapCommentViewCommentData mapCommentViewCommentData;
    private final DateTimeFormatter dateTimeFormatter;
    private final UserRepository userRepository;

    public MapLinkViewLinkData(MapCommentViewCommentData mapCommentViewCommentData, DateTimeFormatter dateTimeFormatter, UserRepository userRepository) {
        this.mapCommentViewCommentData = mapCommentViewCommentData;
        this.dateTimeFormatter = dateTimeFormatter;
        this.userRepository = userRepository;
    }

    ViewLinkData viewLinkData(Link link) {
        return ViewLinkData.builder(mapCommentViewCommentData)
                .id(link.getId())
                .title(link.getTitle())
                .url(link.getUrl())
                .domainName(getDomainName(link))
                .numberOfComments(link.getComments().size())
                .formatCreationDate(dateTimeFormatter.format(link.getCreatedDate()))
                .creationDate(link.getCreatedDate())
                .createdBy(getUserFullName(link))
                .domainComments(link.getComments())
                .build();
    }

    List<ViewLinkData> viewLinkDataList(List<Link> links) {
        return map(links);
    }

    private String getDomainName(Link link) {
        try {
            return link.getDomainName();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return "Error domain name";
    }

    private String getUserFullName(Link link) {
        return userRepository.findByLogin(link.getCreatedBy()).map(User::getUserFullName).orElse("Unknown");
    }

    @Override
    protected ViewLinkData map(Link link, Object... args) {
        return viewLinkData(link);
    }
}