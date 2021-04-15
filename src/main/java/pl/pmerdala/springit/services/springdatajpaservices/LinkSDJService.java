package pl.pmerdala.springit.services.springdatajpaservices;

import pl.pmerdala.springit.datamodel.LinkSchema;
import pl.pmerdala.springit.datamodel.repositories.ProjectRepository;
import pl.pmerdala.springit.model.Link;
import pl.pmerdala.springit.services.LinkService;

public class LinkSDJService extends AbstractProjectSDJService<Link, LinkSchema, Long> implements LinkService {

    public LinkSDJService(ProjectRepository<LinkSchema, Long> linkSchemaRepository, LinkMapper linkMapper) {
        super(linkSchemaRepository, linkMapper);
    }

}
