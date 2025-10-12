package se331.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se331.project.entity.Comment;
import se331.project.entity.News;
import se331.project.repository.CommentRepository;
import se331.project.repository.NewsRepository;
import se331.project.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    final CommentRepository commentRepository;
    final NewsRepository newsRepository;
    final UserRepository userRepository;




    @Override
    @Transactional // this guy use to roll back when it have eeror when delete , make database save

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
}