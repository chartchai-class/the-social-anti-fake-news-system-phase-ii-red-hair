package se331.project.service;

import se331.project.entity.Comment;

public interface CommentService {
    Comment save(Long newsId, Long authorId, Comment comment);
    void deleteById(Long id);

}