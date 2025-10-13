package se331.project.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import se331.project.entity.Comment;
import se331.project.entity.News;
import se331.project.entity.Role;
import se331.project.entity.User;
import se331.project.repository.CommentRepository;
import se331.project.repository.NewsRepository;
import se331.project.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitApp implements ApplicationListener<ApplicationReadyEvent> {

    final UserRepository userRepository;
    final NewsRepository newsRepository;
    final CommentRepository commentRepository;


    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        // user that have differrent role ** idk we should have profile image?
        User admin = User.builder().username("admin").firstName("Admin").lastName("User").email("admin@test.com").password(encoder.encode("password")).roles(List.of(Role.ROLE_ADMIN)).enabled(true).profileImage("https://i.pravatar.cc/150?u=admin").build();
        userRepository.save(admin);

        User member = User.builder().username("member").firstName("Member").lastName("User").email("member@test.com").password(encoder.encode("password")).roles(List.of(Role.ROLE_MEMBER)).enabled(true).profileImage("https://i.pravatar.cc/150?u=member").build();
        userRepository.save(member);

        User reader = User.builder().username("reader").firstName("Reader").lastName("User").email("reader@test.com").password(encoder.encode("password")).roles(List.of(Role.ROLE_READER)).enabled(true).profileImage("https://i.pravatar.cc/150?u=reader").build();
        userRepository.save(reader);


        // News by member
        News news1 = News.builder().title("member news01").category("Technology").reporter(member).newsDateTime(LocalDateTime.now().minusDays(1)).description("A new study reveals that cats are officially the supreme rulers of all online content.").content("In a landmark study, researchers have concluded that the internet is a sophisticated system for sharing cat pictures.").image("https://placekitten.com/800/400").fakeCount(10).notFakeCount(150).build();
        news1.setVoteType(calculateVoteType(news1));
        newsRepository.save(news1);

        News news2 = News.builder().title("member news02").category("Health").reporter(member).newsDateTime(LocalDateTime.now().minusDays(2)).description("A local resident has now seen the light regarding the popular breakfast item.").content("John Doe, 34, today admitted that his long-standing opposition to avocado toast was 'misguided'.").image("https://images.unsplash.com/photo-1582572288450-e2d17c7a5f3a").fakeCount(120).notFakeCount(5).build();
        news2.setVoteType(calculateVoteType(news2));
        newsRepository.save(news2);

        News news3 = News.builder().title("member news03").category("Science").reporter(member).newsDateTime(LocalDateTime.now().minusHours(5)).description("Years of research have finally confirmed what office workers have known for decades.").content("A peer-reviewed study has proven that coffee consumption is directly correlated with the ability to tolerate morning meetings.").image("https://images.unsplash.com/photo-1511920183353-3c2c5d7d549b").fakeCount(2).notFakeCount(200).build();
        news3.setVoteType(calculateVoteType(news3));
        newsRepository.save(news3);

        News news4 = News.builder().title("member news04").category("Local News").reporter(member).newsDateTime(LocalDateTime.now().minusDays(3)).description("Local bird feeders are empty, and authorities are pointing fingers at a highly organized squirrel syndicate.").content("Witnesses report seeing squirrels using complex diversion tactics and advanced climbing techniques.").image("https://images.unsplash.com/photo-1504283284728-def757a35368").fakeCount(50).notFakeCount(55).build();
        news4.setVoteType(calculateVoteType(news4));
        newsRepository.save(news4);

        News news5 = News.builder().title("member news05").category("Conspiracy").reporter(member).newsDateTime(LocalDateTime.now().minusDays(5)).description("A growing online community claims that water isn't real. We investigate.").content("The 'Dry Earth' society posits that what we perceive as water is actually a complex government-induced hallucination.").image("https://images.unsplash.com/photo-1523362628745-0c371c17b446").fakeCount(250).notFakeCount(3).build();
        news5.setVoteType(calculateVoteType(news5));
        newsRepository.save(news5);

        News news6 = News.builder().title("member news06").category("Lifestyle").reporter(member).newsDateTime(LocalDateTime.now().minusDays(4)).description("By putting off tasks, you can effectively arrive in the future without doing any of the work.").content("Researchers have noted that by deciding to do something 'tomorrow,' subjects could skip an entire day of productivity.").image("https://images.unsplash.com/photo-1486312338219-ce68d2c6f44d").fakeCount(33).notFakeCount(42).build();
        news6.setVoteType(calculateVoteType(news6));
        newsRepository.save(news6);

        //comment by role
        Comment comment1 = Comment.builder().news(news1).author(reader).content("Test comment01").voteType("not-fake").commentDateTime(LocalDateTime.now().minusHours(10)).build();
        commentRepository.save(comment1);

        Comment comment2 = Comment.builder().news(news1).author(admin).content(" Test comment02.").voteType("not-fake").commentDateTime(LocalDateTime.now().minusHours(9)).build();
        commentRepository.save(comment2);

        Comment comment3 = Comment.builder().news(news2).author(reader).content("Test comment03.").voteType("fake").commentDateTime(LocalDateTime.now().minusDays(1)).build();
        commentRepository.save(comment3);
    }

   // just to help calculate
    private String calculateVoteType(News news) {
        if (news.getFakeCount() > news.getNotFakeCount()) {
            return "fake";
        } else {
            return "not-fake";
        }
    }
}