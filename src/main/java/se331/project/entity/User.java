package se331.project.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    String profileImage; // idk we should have this?
//user post many new
    @OneToMany(mappedBy = "reporter")
    @JsonManagedReference("user-news")
    @Builder.Default
    private List<News> reportedNews = new ArrayList<>();
// user can write many comment
    @OneToMany(mappedBy = "author")
    @Builder.Default
    @JsonManagedReference("user-comment")
    private List<Comment> comments = new ArrayList<>();
}