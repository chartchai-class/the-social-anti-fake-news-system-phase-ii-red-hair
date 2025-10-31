package se331.project.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.project.entity.Comment;

public interface CommentDao {
    Comment save(Comment comment);
    Comment updateIsDeleted(Long id, Boolean isDeleted);
    Page<Comment> getCommentsByNewsId(Long newsId, Pageable pageable);
    Page<Comment> getCommentsByNewsIdByAdmin(Long newsId, Pageable pageable);
}