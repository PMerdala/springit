package pl.pmerdala.springit.services.springdatajpaservices;

import pl.pmerdala.springit.datamodel.LinkSchema;
import pl.pmerdala.springit.model.Link;

import java.util.Optional;

public class LinkMapper implements Mapper<LinkSchema, Link> {
    @Override
    public Optional<Link> map(Optional<LinkSchema> source) {

        return source.map(s -> new Link(s.getId(), s.getTitle(), s.getUrl()));
    }

    @Override
    public Optional<LinkSchema> reversMap(Optional<Link> source) {
        return source.map(s -> new LinkSchema(s.getId(),s.getTitle(),s.getUrl()));
    }
}
