package pl.pmerdala.springit.services.springdatajpaservices;

import pl.pmerdala.springit.datamodel.CommentSchema;
import pl.pmerdala.springit.datamodel.repositories.CommentSchemaRepository;
import pl.pmerdala.springit.model.Comment;
import pl.pmerdala.springit.services.CommentService;

public class CommentSDJService extends AbstractProjectSDJService<Comment, CommentSchema, Long> implements CommentService {
    public CommentSDJService(CommentSchemaRepository repository, CommentMapper mapper) {
        super(repository, mapper);
    }
}
