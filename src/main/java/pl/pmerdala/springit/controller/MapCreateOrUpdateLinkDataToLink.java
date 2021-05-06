package pl.pmerdala.springit.controller;

import org.springframework.stereotype.Component;
import pl.pmerdala.springit.domain.Link;

@Component
public class MapCreateOrUpdateLinkDataToLink {

    Link link(CreateOrUpdateLinkData data) {
        return new Link(data.getTitle(), data.getUrl(), data.getDescription());
    }

    void updateLink(CreateOrUpdateLinkData data, Link link) {
        link.setTitle(data.getTitle());
        link.setUrl(data.getUrl());
        link.setDescription(data.getDescription());
    }

}