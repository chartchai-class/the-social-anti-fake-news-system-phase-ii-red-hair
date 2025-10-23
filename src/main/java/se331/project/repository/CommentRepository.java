package se331.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import se331.project.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByNews_IdAndIsDeletedFalse(Long news_id, Pageable pageable);
    Page<Comment> findByNews_Id(Long news_id, Pageable pageable);
}