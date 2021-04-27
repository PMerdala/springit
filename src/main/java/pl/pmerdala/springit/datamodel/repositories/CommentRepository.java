package pl.pmerdala.springit.datamodel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pmerdala.springit.datamodel.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
