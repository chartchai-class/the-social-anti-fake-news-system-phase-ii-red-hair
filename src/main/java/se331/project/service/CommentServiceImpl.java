package se331.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se331.project.dao.CommentDao;
import se331.project.entity.Comment;
import se331.project.entity.News;
import se331.project.entity.User;
import se331.project.repository.NewsRepository;
import se331.project.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    final CommentDao commentDao;
    final NewsRepository newsRepository;
    final UserRepository userRepository;

    @Override
    // it look Strangely complicated maybe will change later
    // logic for create new comment and link to new and user
    // this is explan how it work
    public Comment save(Long newsId, Long authorId, Comment comment) {
        // it will find the related news and user form data
        News news = newsRepository.findById(newsId).orElse(null);
        User author = userRepository.findById(authorId).orElse(null);

        // then if it found , it gonna link to the new comemnt
        if (news != null && author != null) {
            comment.setNews(news);
            comment.setAuthor(author);
            comment.setCommentDateTime(LocalDateTime.now());

            // this is the update counts on the News entity
            if (comment.getVoteType().equals("fake")) {
                news.setFakeCount(news.getFakeCount() + 1);
            } else if (comment.getVoteType().equals("not-fake")) {
                news.setNotFakeCount(news.getNotFakeCount() + 1);
            }

            // and this is save the updated new and the new comment
            newsRepository.save(news);
            return commentDao.save(comment);
        }
        return null;
    }
}