package se331.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import se331.project.security.token.Token;
import se331.project.security.user.Role;
import se331.project.security.user.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
    private String username;
    private String email;
    private Boolean enabled;
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