package pl.pmerdala.springit.services.springdatajpaservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pmerdala.springit.datamodel.VoteSchema;
import pl.pmerdala.springit.model.Vote;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VoteMapperTest {

    public static final long ID = 1;
    public static final int VOTE = 1;
    private VoteMapper sut;


    @BeforeEach
    void setUp() {
        sut = new VoteMapper();
    }

    @Test
    void mapNull() {
        assertTrue(sut.map(Optional.empty()).isEmpty());
    }

    @Test
    void map() {
        VoteSchema voteSchema = new VoteSchema();
        voteSchema.setId(ID);
        voteSchema.setVote(VOTE);
        Optional<Vote> vote = sut.map(Optional.of(voteSchema));
        assertTrue(vote.isPresent());
        assertEquals(ID, vote.get().getId());
        assertEquals(VOTE, vote.get().getVote());

    }

    @Test
    void reversMapNull() {
        assertTrue(sut.reversMap(Optional.empty()).isEmpty());
    }

    @Test
    void reversMap() {
        Vote vote = new Vote(ID, VOTE);
        Optional<VoteSchema> voteSchema = sut.reversMap(Optional.of(vote));
        assertTrue(voteSchema.isPresent());
        assertEquals(ID, voteSchema.get().getId());
        assertEquals(VOTE, voteSchema.get().getVote());
    }

}