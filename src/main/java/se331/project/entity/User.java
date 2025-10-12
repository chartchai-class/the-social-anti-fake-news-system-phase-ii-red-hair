package se331.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String firstName;
    String lastName;
    String email;
    String password;
    String role; // idk sort later
    String profileImage;
//user post many new
    @OneToMany(mappedBy = "reporter")
    @Builder.Default
    private List<News> reportedNews = new ArrayList<>();
// user can write many comment
    @OneToMany(mappedBy = "author")
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();
}