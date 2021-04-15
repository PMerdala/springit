package pl.pmerdala.springit.services.springdatajpaservices;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import pl.pmerdala.springit.datamodel.repositories.LinkSchemaRepository;
import pl.pmerdala.springit.datamodel.repositories.VoteSchemaRepository;

import static org.junit.jupiter.api.Assertions.*;

class VoteSDJServiceTest {
    @Mock
    VoteSchemaRepository repository;

    VoteMapper mapper;

    VoteSDJService sut;

    @BeforeEach
    void setUp() {
        mapper = new VoteMapper();
        sut = new VoteSDJService(repository,mapper);
    }

}