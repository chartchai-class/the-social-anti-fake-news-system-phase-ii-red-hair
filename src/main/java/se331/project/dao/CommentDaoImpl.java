package se331.project.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se331.project.entity.Comment;
import se331.project.repository.CommentRepository;

@Repository
@RequiredArgsConstructor
public class CommentDaoImpl implements CommentDao {
    final CommentRepository commentRepository;
// i want to try to use form repo direct so this part will not be use
    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }
}
