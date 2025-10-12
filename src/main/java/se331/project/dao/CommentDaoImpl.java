package se331.project.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se331.project.entity.Comment;
import se331.project.repository.CommentRepository;

@Repository
@RequiredArgsConstructor
public class CommentDaoImpl implements CommentDao {
    final CommentRepository commentRepository;

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }
}