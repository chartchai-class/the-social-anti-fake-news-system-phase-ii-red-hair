package se331.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.project.entity.Comment;
import se331.project.dto.CommentDto;

public interface CommentService {
    CommentDto save(Long newsId, Long authorId, Comment comment);
    void deleteById(Long id);
    void updateIsDeleted(Long id, Boolean isDeleted);
    Page<CommentDto> getCommentsByNewsId(Long newsId, Pageable pageable);
    Page<CommentDto> getCommentsByNewsIdByAdmin(Long newsId,Pageable pageable);
}