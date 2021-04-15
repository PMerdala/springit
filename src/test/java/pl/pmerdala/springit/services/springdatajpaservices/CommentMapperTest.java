package pl.pmerdala.springit.services.springdatajpaservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pmerdala.springit.datamodel.CommentSchema;
import pl.pmerdala.springit.model.Comment;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CommentMapperTest {
    public static final long ID = 1;
    public static final String COMMENT = "comment";
    private CommentMapper sut;


    @BeforeEach
    void setUp() {
        sut = new CommentMapper();
    }

    @Test
    void mapNull() {
        assertTrue(sut.map(Optional.empty()).isEmpty());
    }

    @Test
    void map() {
        CommentSchema commentSchema = new CommentSchema();
        commentSchema.setId(ID);
        commentSchema.setComment(COMMENT);
        Optional<Comment> comment = sut.map(Optional.of(commentSchema));
        assertTrue(comment.isPresent());
        assertEquals(ID, comment.get().getId());
        assertEquals(COMMENT, comment.get().getComment());

    }

    @Test
    void reversMapNull() {
        assertTrue(sut.reversMap(Optional.empty()).isEmpty());
    }

    @Test
    void reversMap() {
        Comment comment = new Comment(ID, COMMENT);
        Optional<CommentSchema> commentSchema = sut.reversMap(Optional.of(comment));
        assertTrue(commentSchema.isPresent());
        assertEquals(ID, commentSchema.get().getId());
        assertEquals(COMMENT, commentSchema.get().getComment());
    }


}