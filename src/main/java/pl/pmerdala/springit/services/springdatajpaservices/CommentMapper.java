package pl.pmerdala.springit.services.springdatajpaservices;

import pl.pmerdala.springit.datamodel.CommentSchema;
import pl.pmerdala.springit.model.Comment;

import java.util.Optional;

public class CommentMapper implements Mapper<CommentSchema, Comment> {
    @Override
    public Optional<Comment> map(Optional<CommentSchema> source) {
        return source.map(s->new Comment(s.getId(),s.getComment()));
    }

    @Override
    public Optional<CommentSchema> reversMap(Optional<Comment> source) {
        return source.map(s->new CommentSchema(s.getId(),s.getComment()));
    }
}
