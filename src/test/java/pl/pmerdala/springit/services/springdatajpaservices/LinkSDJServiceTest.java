package pl.pmerdala.springit.services.springdatajpaservices;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import pl.pmerdala.springit.datamodel.repositories.CommentSchemaRepository;
import pl.pmerdala.springit.datamodel.repositories.LinkSchemaRepository;

import static org.junit.jupiter.api.Assertions.*;

class LinkSDJServiceTest {
    @Mock
    LinkSchemaRepository repository;

    LinkMapper mapper;

    LinkSDJService sut;

    @BeforeEach
    void setUp() {
        mapper = new LinkMapper();
        sut = new LinkSDJService(repository,mapper);
    }

}