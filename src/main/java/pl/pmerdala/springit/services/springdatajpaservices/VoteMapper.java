package pl.pmerdala.springit.services.springdatajpaservices;

import pl.pmerdala.springit.datamodel.VoteSchema;
import pl.pmerdala.springit.model.Vote;

import java.util.Optional;

public class VoteMapper implements Mapper<VoteSchema, Vote> {
    @Override
    public Optional<Vote> map(Optional<VoteSchema> source) {
        return source.map(s->new Vote(s.getId(),s.getVote()));
    }

    @Override
    public Optional<VoteSchema> reversMap(Optional<Vote> source) {
        return source.map(s->new VoteSchema(s.getId(),s.getVote()));
    }
}
