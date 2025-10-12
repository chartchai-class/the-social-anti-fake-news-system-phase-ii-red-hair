package se331.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se331.project.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}