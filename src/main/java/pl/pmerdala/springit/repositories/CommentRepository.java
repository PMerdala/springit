package pl.pmerdala.springit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pmerdala.springit.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
