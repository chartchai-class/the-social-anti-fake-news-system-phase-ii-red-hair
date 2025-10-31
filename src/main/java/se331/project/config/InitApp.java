package se331.project.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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


import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class InitApp implements ApplicationListener<ApplicationReadyEvent> {
    private final ObjectMapper objectMapper;

    final UserProfileRepository userProfileRepository;
    final NewsRepository newsRepository;
    final CommentRepository commentRepository;
    final UserRepository userRepository;


    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {

        //Set up user and user profiles
        addUser();
        addUserProfile();

        admin1.setUserProfile(adminProfile1);
        member1.setUserProfile(memberProfile1);
        member2.setUserProfile(memberProfile2);
        member3.setUserProfile(memberProfile3);
        reader1.setUserProfile(readerProfile1);
        reader2.setUserProfile(readerProfile2);
        userRepository.saveAll(List.of(admin1, member1, member2, member3, reader1, reader2));

        adminProfile1.setUser(admin1);
        memberProfile1.setUser(member1);
        memberProfile2.setUser(member2);
        memberProfile3.setUser(member3);
        readerProfile1.setUser(reader1);
        readerProfile2.setUser(reader2);
        userProfileRepository.saveAll(List.of(adminProfile1, memberProfile1, memberProfile2, memberProfile3, readerProfile1, readerProfile2));



        //Create News and comments
        try(InputStream inputStream = getClass().getResourceAsStream("/data/news.json");){
            List<Map<String, Object>> newsList = objectMapper.readValue(inputStream, new TypeReference<>(){});

            for(Map<String, Object> singleNews: newsList){
                News news = News.builder()
                        .title((String) singleNews.get("title"))
                        .category((String) singleNews.get("category"))
                        .reporter(getRandomUserProfile())
                        .newsDateTime(LocalDateTime.now().minusDays(1))
                        .description((String) singleNews.get("description"))
                        .content((String) singleNews.get("content"))
                        .image((String) singleNews.get("image"))
                        .fakeCount((Integer) singleNews.get("fakeCount"))
                        .notFakeCount((Integer) singleNews.get("notFakeCount"))
                        .isDeleted(false)
                        .build();
                news.setVoteType(calculateVoteType(news));
                newsRepository.save(news);

                // get comments for news if exists
                List<Map<String, Object>> comments = (List<Map<String, Object>>) singleNews.get("comments");
                if(comments!=null && comments.size()>0){
                    for(Map<String,Object> singleComment : comments){
                        Comment comment = Comment.builder()
                                .news(news)
                                .author(getRandomUserProfile())
                                .content((String) singleComment.get("content"))
                                .image((String) singleComment.get("image"))
                                .voteType((String) singleComment.get("voteType"))
                                .commentDateTime(LocalDateTime.now())
                                .isDeleted(false)
                                .build();
                        commentRepository.save(comment);
                    }
                }
            }
        }catch(Error | IOException error){
            System.out.println(error.getMessage());
        }

    }


    // create users and userprofiles by hardcode, I thought its more easier to add this way if there is only few users.
    User admin1, member1, member2, member3, reader1, reader2;
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

        member2 = User.builder()
                .username("member2")
                .password(passwordEncoder.encode("password"))
                .enabled(true)
                .build();

        member3 = User.builder()
                .username("member3")
                .password(passwordEncoder.encode("password"))
                .enabled(true)
                .build();

        reader1 = User.builder()
                .username("reader")
                .password(passwordEncoder.encode("password"))
                .enabled(true)
                .build();

        reader2 = User.builder()
                .username("reader2")
                .password(passwordEncoder.encode("password"))
                .enabled(true)
                .build();

        admin1.getRoles().add(Role.ROLE_ADMIN);
        member1.getRoles().add(Role.ROLE_MEMBER);
        member2.getRoles().add(Role.ROLE_MEMBER);
        member3.getRoles().add(Role.ROLE_MEMBER);
        reader1.getRoles().add(Role.ROLE_READER);
        reader2.getRoles().add(Role.ROLE_READER);

        userRepository.saveAll(List.of(admin1, member1, member2, member3, reader1, reader2));
    }

    UserProfile adminProfile1, memberProfile1, memberProfile2, memberProfile3, readerProfile1, readerProfile2;
    private void addUserProfile(){
        adminProfile1 = UserProfile.builder()
                .displayName("Starlight Anya")
                .firstName("Anya")
                .lastName("Forger")
                .email("admin@gmail.com")
                .profileImage("https://i.pinimg.com/736x/32/82/8a/32828a1554ad31ff44c5d4bd948cfdd7.jpg")
                .phoneNumber("123456789")
                .build();

        memberProfile1 = UserProfile.builder()
                .displayName("Twilight")
                .firstName("Loid")
                .lastName("Forger")
                .email("loid@gmail.com")
                .profileImage("https://i.pinimg.com/736x/f9/b1/74/f9b174063029cd681bb79b639c8d5d8c.jpg")
                .phoneNumber("123456789")
                .build();

        memberProfile2 = UserProfile.builder()
                .displayName("David")
                .firstName("David")
                .lastName("Beckham")
                .email("david@gmail.com")
                .profileImage("https://i.pinimg.com/736x/86/a5/c3/86a5c37c37d23f7536736592a4ded72e.jpg")
                .phoneNumber("123456789")
                .build();

        memberProfile3 = UserProfile.builder()
                .displayName("Cutie Emily")
                .firstName("Emily")
                .lastName("Stark")
                .email("emily@gmail.com")
                .profileImage("https://i.pinimg.com/736x/af/ab/f2/afabf2b1a33b7a5f0e87fd5b28f06963.jpg")
                .phoneNumber("09889923")
                .build();

        readerProfile1 = UserProfile.builder()
                .displayName("Thorn Princess")
                .firstName("Yor")
                .lastName("Forger")
                .email("yor@test.com")
                .profileImage("https://i.pinimg.com/1200x/eb/8c/77/eb8c771917321bfe3873970d7793458f.jpg")
                .phoneNumber("123456789")
                .build();

        readerProfile2 = UserProfile.builder()
                .displayName("Rosy")
                .firstName("Rosy")
                .lastName("Posy")
                .email("rosy@gmail.com")
                .profileImage("https://i.pinimg.com/736x/8b/07/c2/8b07c2157f3f96e50d7cc1741d78c2f3.jpg")
                .phoneNumber("123456789")
                .build();

        userProfileRepository.saveAll(List.of(adminProfile1, memberProfile1, memberProfile2, memberProfile3, readerProfile1, readerProfile2));
    }


    // just to help calculate
    private String calculateVoteType(News news) {
        if (news.getFakeCount() > news.getNotFakeCount()) {
            return "fake";
        } else {
            return "not-fake";
        }
    }

    // to assign random users as a news reporter
    private UserProfile getRandomUserProfile(){
        List<UserProfile> userProfileList= List.of(adminProfile1, memberProfile1, memberProfile2, memberProfile3);
        int randomIndex = new Random().nextInt(userProfileList.size());

        return userProfileList.get(randomIndex);
    }
}