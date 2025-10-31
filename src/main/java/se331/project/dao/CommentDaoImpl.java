package se331.project.dao;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Comment updateIsDeleted(Long id, Boolean isDeleted) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment == null){
            throw new EntityNotFoundException("comment not found");
        }
        if(isDeleted == null){
            throw new EntityNotFoundException("isDeleted must not be null");
        }
        if(comment.getIsDeleted() == isDeleted){ return comment; };

        comment.setIsDeleted(isDeleted);
        return commentRepository.save(comment);
    }

    // for non-admin users
    @Override
    public Page<Comment> getCommentsByNewsId(Long newsId, Pageable pageRequest){
        return commentRepository.findByNews_IdAndIsDeletedFalse(newsId,pageRequest);
    }

    // for admin
    @Override
    public Page<Comment> getCommentsByNewsIdByAdmin(Long newsId, Pageable pageRequest) {
        return commentRepository.findByNews_Id(newsId, pageRequest);
    }
}
