package pl.pmerdala.springit.services.springdatajpaservices;

import pl.pmerdala.springit.datamodel.VoteSchema;
import pl.pmerdala.springit.datamodel.repositories.ProjectRepository;
import pl.pmerdala.springit.model.Vote;
import pl.pmerdala.springit.services.VoteService;

public class VoteSDJService extends AbstractProjectSDJService<Vote, VoteSchema,Long> implements VoteService {
    public VoteSDJService(ProjectRepository<VoteSchema, Long> repository, Mapper<VoteSchema, Vote> mapper) {
        super(repository, mapper);
    }
}
