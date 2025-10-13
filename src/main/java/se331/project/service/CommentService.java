package se331.project.service;

import se331.project.entity.Comment;
import se331.project.dto.CommentDto;

public interface CommentService {
    CommentDto save(Long newsId, Long authorId, Comment comment);
    void deleteById(Long id);

}