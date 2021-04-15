package pl.pmerdala.springit.services.springdatajpaservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pmerdala.springit.datamodel.LinkSchema;
import pl.pmerdala.springit.model.Link;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LinkMapperTest {

    public static final long ID = 1;
    public static final String TITLE = "title";
    public static final String URL = "url";
    private LinkMapper sut;

    @BeforeEach
    void setUp() {
        sut = new LinkMapper();
    }

    @Test
    void mapNull() {
        assertTrue(sut.map(Optional.empty()).isEmpty());
    }

    @Test
    void map() {
        LinkSchema linkSchema = new LinkSchema();
        linkSchema.setId(ID);
        linkSchema.setTitle(TITLE);
        linkSchema.setUrl(URL);
        Optional<Link> link = sut.map(Optional.of(linkSchema));
        assertTrue(link.isPresent());
        assertEquals(ID, link.get().getId());
        assertEquals(TITLE, link.get().getTitle());
        assertEquals(URL, link.get().getUrl());

    }

    @Test
    void reversMapNull() {
        assertTrue(sut.reversMap(Optional.empty()).isEmpty());
    }

    @Test
    void reversMap() {
        Link link = new Link(ID, TITLE, URL);
        Optional<LinkSchema> linkSchema = sut.reversMap(Optional.of(link));
        assertTrue(linkSchema.isPresent());
        assertEquals(ID, linkSchema.get().getId());
        assertEquals(TITLE, linkSchema.get().getTitle());
        assertEquals(URL, linkSchema.get().getUrl());
    }
}