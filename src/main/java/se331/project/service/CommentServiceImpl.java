package se331.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import se331.project.dao.CommentDao;
import se331.project.dto.CommentDto;
import se331.project.entity.Comment;
import se331.project.entity.News;
import se331.project.entity.UserProfile;
import se331.project.repository.CommentRepository;
import se331.project.repository.NewsRepository;
import se331.project.repository.UserProfileRepository;
import se331.project.util.AMapper;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    final CommentRepository commentRepository;
    final NewsRepository newsRepository;
    final UserProfileRepository userProfileRepository;

    final CommentDao commentDao;

    @Override
    @Transactional
    // method for add new comement and it also can update vote fake/notfake
    public CommentDto save(Long newsId, Long authorId, Comment comment) {
        //find id comment
        News relatedNews = newsRepository.findById(newsId).orElse(null);
        UserProfile author = userProfileRepository.findById(authorId).orElse(null);

        if (relatedNews == null || author == null) {
            return null;
        }

        //setting data comemmt object
        comment.setNews(relatedNews);
        comment.setAuthor(author);
        comment.setCommentDateTime(LocalDateTime.now());

        //updatevote
        if ("fake".equals(comment.getVoteType())) {
            relatedNews.setFakeCount(relatedNews.getFakeCount() + 1);
        } else if ("not-fake".equals(comment.getVoteType())) {
            relatedNews.setNotFakeCount(relatedNews.getNotFakeCount() + 1);
        }
        relatedNews.setVoteType(computeVoteTypeForNews(relatedNews)); //I think we need to calculate and send from backend instead of calculating in frontend. feel free to delete this if there is already compution in another place ( i probably might have missed that).

        // save all change
        newsRepository.save(relatedNews);


        Comment savedComment = commentRepository.save(comment);
        return AMapper.INSTANCE.getCommentDto(savedComment);
    }

    @Override
    @Transactional // i just found this is so op this guy use to roll back when it have eeror when delete , make database save

    // this method can delete comment by id and it will count new vote for the news that have the deleted comment
    public void deleteById(Long id) {

        //find id comment for delete
        Comment commentToDelete = commentRepository.findById(id).orElse(null);

        if (commentToDelete == null) {
            return;
        }

        // pull data
        News relatedNews = commentToDelete.getNews();

        // check that this commend related the news
        if (relatedNews != null) {

            //check votetype
            if ("fake".equals(commentToDelete.getVoteType())) {
                // if fake decress 1  fake count
                relatedNews.setFakeCount(relatedNews.getFakeCount() - 1);

            } else if ("not-fake".equals(commentToDelete.getVoteType())) {
                // not fake also delete one not fake count
                relatedNews.setNotFakeCount(relatedNews.getNotFakeCount() - 1);
            }

            //save data that get update
            newsRepository.save(relatedNews);
        }

        //delete comment in database
        commentRepository.delete(commentToDelete);
    }

    @Override
    @Transactional
    public void updateIsDeleted(Long id, Boolean isDeleted) {
        Comment commentToUpdate= commentRepository.findById(id).orElse(null);
        if (commentToUpdate == null) { return;}
        if(commentToUpdate.getIsDeleted() == isDeleted){ return; };

        // manage vote type for news before deleting
        News relatedNews = commentToUpdate.getNews();
        if (relatedNews != null) {
            if ("fake".equals(commentToUpdate.getVoteType()) && isDeleted==false) {
                relatedNews.setFakeCount(relatedNews.getFakeCount() + 1);
            } else if ("not-fake".equals(commentToUpdate.getVoteType()) && isDeleted==false) {
                relatedNews.setNotFakeCount(relatedNews.getNotFakeCount() + 1);
            } else if ("fake".equals(commentToUpdate.getVoteType()) && isDeleted==true) {
                relatedNews.setFakeCount(relatedNews.getFakeCount() - 1);
            } else if ("not-fake".equals(commentToUpdate.getVoteType()) && isDeleted==true) {
                relatedNews.setNotFakeCount(relatedNews.getNotFakeCount() - 1);
            }
            relatedNews.setVoteType(computeVoteTypeForNews(relatedNews));
            newsRepository.save(relatedNews);
        }

        // actual soft delete from repo -- my code seems redundant here for calling comment id to find comment and update again in the method. feel free to refactor later.
        commentDao.updateIsDeleted(id, isDeleted);
    }

    // non-admin users can get only visible comments
    @Override
    public Page<CommentDto> getCommentsByNewsId(Long newsId,Pageable pageRequest){
        Page<Comment> commentPage = commentDao.getCommentsByNewsId(newsId, pageRequest);

        return commentPage.map(comment -> AMapper.INSTANCE.getCommentDto(comment));
    }

    // admin gets all comments including deleted
    @Override
    public Page<CommentDto> getCommentsByNewsIdByAdmin(Long newsId, Pageable pageRequest) {
        Page<Comment> commentPage = commentDao.getCommentsByNewsIdByAdmin(newsId, pageRequest);

        return commentPage.map(comment -> AMapper.INSTANCE.getCommentDto(comment));
    }

    //helper
    private String computeVoteTypeForNews(News news){
        return news.getFakeCount()>news.getNotFakeCount()?"fake":"not-fake";
    }
}