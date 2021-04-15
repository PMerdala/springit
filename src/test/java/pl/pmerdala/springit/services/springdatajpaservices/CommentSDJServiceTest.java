package pl.pmerdala.springit.services.springdatajpaservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pmerdala.springit.datamodel.repositories.CommentSchemaRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommentSDJServiceTest {

    @Mock
    CommentSchemaRepository repository;

    CommentMapper mapper;

    CommentSDJService sut;

    @BeforeEach
    void setUp() {
        mapper = new CommentMapper();
        sut = new CommentSDJService(repository,mapper);
    }
}