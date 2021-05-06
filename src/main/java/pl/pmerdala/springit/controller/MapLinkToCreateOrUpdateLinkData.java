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
public class MapLinkToCreateOrUpdateLinkData{

    CreateOrUpdateLinkData createOrUpdateLinkData(Link link) {
        return CreateOrUpdateLinkData.builder()
                .title(link.getTitle())
                .url(link.getUrl())
                .description(link.getDescription())
                .build();
    }

}