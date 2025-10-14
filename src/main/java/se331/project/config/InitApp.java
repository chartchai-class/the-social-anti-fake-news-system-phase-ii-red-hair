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
import se331.project.entity.UserProfile;
import se331.project.repository.UserProfileRepository;
import se331.project.security.user.Role;
import se331.project.repository.CommentRepository;
import se331.project.repository.NewsRepository;
import se331.project.security.user.User;
import se331.project.security.user.UserRepository;


import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitApp implements ApplicationListener<ApplicationReadyEvent> {

    final UserProfileRepository userProfileRepository;
    final NewsRepository newsRepository;
    final CommentRepository commentRepository;
    final UserRepository userRepository;


    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {

        addUser();
        addUserProfile();

        admin1.setUserProfile(adminProfile1);
        member1.setUserProfile(memberProfile1);
        reader1.setUserProfile(readerProfile1);

        adminProfile1.setUser(admin1);
        memberProfile1.setUser(member1);
        readerProfile1.setUser(reader1);

        // News by member
        News news1 = News.builder().title("member news01").category("Technology").reporter(memberProfile1).newsDateTime(LocalDateTime.now().minusDays(1)).description("A new study reveals that cats are officially the supreme rulers of all online content.").content("In a landmark study, researchers have concluded that the internet is a sophisticated system for sharing cat pictures.").image("https://placekitten.com/800/400").fakeCount(10).notFakeCount(150).build();
        news1.setVoteType(calculateVoteType(news1));
        newsRepository.save(news1);

        News news2 = News.builder().title("member news02").category("Health").reporter(memberProfile1).newsDateTime(LocalDateTime.now().minusDays(2)).description("A local resident has now seen the light regarding the popular breakfast item.").content("John Doe, 34, today admitted that his long-standing opposition to avocado toast was 'misguided'.").image("https://images.unsplash.com/photo-1582572288450-e2d17c7a5f3a").fakeCount(120).notFakeCount(5).build();
        news2.setVoteType(calculateVoteType(news2));
        newsRepository.save(news2);

        News news3 = News.builder().title("member news03").category("Science").reporter(memberProfile1).newsDateTime(LocalDateTime.now().minusHours(5)).description("Years of research have finally confirmed what office workers have known for decades.").content("A peer-reviewed study has proven that coffee consumption is directly correlated with the ability to tolerate morning meetings.").image("https://images.unsplash.com/photo-1511920183353-3c2c5d7d549b").fakeCount(2).notFakeCount(200).build();
        news3.setVoteType(calculateVoteType(news3));
        newsRepository.save(news3);

        News news4 = News.builder().title("member news04").category("Local News").reporter(memberProfile1).newsDateTime(LocalDateTime.now().minusDays(3)).description("Local bird feeders are empty, and authorities are pointing fingers at a highly organized squirrel syndicate.").content("Witnesses report seeing squirrels using complex diversion tactics and advanced climbing techniques.").image("https://images.unsplash.com/photo-1504283284728-def757a35368").fakeCount(50).notFakeCount(55).build();
        news4.setVoteType(calculateVoteType(news4));
        newsRepository.save(news4);

        News news5 = News.builder().title("member news05").category("Conspiracy").reporter(memberProfile1).newsDateTime(LocalDateTime.now().minusDays(5)).description("A growing online community claims that water isn't real. We investigate.").content("The 'Dry Earth' society posits that what we perceive as water is actually a complex government-induced hallucination.").image("https://images.unsplash.com/photo-1523362628745-0c371c17b446").fakeCount(250).notFakeCount(3).build();
        news5.setVoteType(calculateVoteType(news5));
        newsRepository.save(news5);

        News news6 = News.builder().title("member news06").category("Lifestyle").reporter(memberProfile1).newsDateTime(LocalDateTime.now().minusDays(4)).description("By putting off tasks, you can effectively arrive in the future without doing any of the work.").content("Researchers have noted that by deciding to do something 'tomorrow,' subjects could skip an entire day of productivity.").image("https://images.unsplash.com/photo-1486312338219-ce68d2c6f44d").fakeCount(33).notFakeCount(42).build();
        news6.setVoteType(calculateVoteType(news6));
        newsRepository.save(news6);

        //comment by role
        Comment comment1 = Comment.builder().news(news1).author(readerProfile1).content("Test comment01").voteType("not-fake").commentDateTime(LocalDateTime.now().minusHours(10)).build();
        commentRepository.save(comment1);

        Comment comment2 = Comment.builder().news(news1).author(readerProfile1).content(" Test comment02.").voteType("not-fake").commentDateTime(LocalDateTime.now().minusHours(9)).build();
        commentRepository.save(comment2);

        Comment comment3 = Comment.builder().news(news2).author(readerProfile1).content("Test comment03.").voteType("fake").commentDateTime(LocalDateTime.now().minusDays(1)).build();
        commentRepository.save(comment3);

        Comment comment4 = Comment.builder().news(news1).author(readerProfile1).content("Test comment04").voteType("not-fake").commentDateTime(LocalDateTime.now().minusHours(10)).build();
        commentRepository.save(comment4);



    }


    //define users here (not profile yet, sry my naming seems confusing, User is only for security layer, and UserProfile is for business layer)
    User admin1, member1, reader1;
    private void addUser(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        admin1 = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("password"))
                .enabled(true)
                .build();

        member1 = User.builder()
                .username("member")
                .password(passwordEncoder.encode("password"))
                .enabled(true)
                .build();

        reader1 = User.builder()
                .username("reader")
                .password(passwordEncoder.encode("password"))
                .enabled(true)
                .build();

        admin1.getRoles().addAll(List.of(Role.ROLE_ADMIN, Role.ROLE_MEMBER, Role.ROLE_READER));
        member1.getRoles().addAll(List.of(Role.ROLE_MEMBER, Role.ROLE_READER));
        reader1.getRoles().add(Role.ROLE_READER);

        userRepository.saveAll(List.of(admin1, member1, reader1));
    }

    UserProfile adminProfile1, memberProfile1, readerProfile1;
    private void addUserProfile(){
        adminProfile1 = UserProfile.builder()
                .displayName("admin")
                .firstName("admin_firstname")
                .lastName("admin_lastname")
                .email("admin@test.com")
                .profileImage("https://i.pinimg.com/736x/32/82/8a/32828a1554ad31ff44c5d4bd948cfdd7.jpg")
                .phoneNumber("123456789")
                .build();
        userProfileRepository.save(adminProfile1);

        memberProfile1 = UserProfile.builder()
                .displayName("member")
                .firstName("member_firstname")
                .lastName("member_lastname")
                .email("member@test.com")
                .profileImage("https://i.pinimg.com/736x/e7/b7/85/e7b785d2cba3004447e07b421391b2fd.jpg")
                .phoneNumber("123456789")
                .build();
        userProfileRepository.save(memberProfile1);

        readerProfile1 = UserProfile.builder()
                .displayName("reader")
                .firstName("reader_firstname")
                .lastName("reader_lastname")
                .email("reader@test.com")
                .profileImage("https://i.pinimg.com/736x/39/86/91/398691f123726a5763e9c47980964fff.jpg")
                .phoneNumber("123456789")
                .build();
        userProfileRepository.save(readerProfile1);
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