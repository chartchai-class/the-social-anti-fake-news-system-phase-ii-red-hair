package se331.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se331.project.security.user.User;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user_profile")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String displayName; //for display only
    private String email;
    private String profileImage;
    private String phoneNumber;

    @OneToMany(mappedBy = "reporter")   // user posts many new
    @Builder.Default
    private List<News> reportedNews = new ArrayList<>();

    @OneToMany(mappedBy = "author") // user can write many comments
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    @OneToOne
    User user;
}