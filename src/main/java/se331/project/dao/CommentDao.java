package se331.project.dao;

import se331.project.entity.Comment;

public interface CommentDao {
    Comment save(Comment comment);
}