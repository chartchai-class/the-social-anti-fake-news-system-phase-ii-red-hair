package se331.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String category;
    private LocalDateTime newsDateTime;

    @Column
    private String description;

    @Column
    private String content;

    private String image; //  url for image
    private Integer fakeCount;
    private Integer notFakeCount;

    // new can oly by one user
    @ManyToOne
    private User reporter;

    // new have many comment
    @OneToMany(mappedBy = "news")
    @Builder.Default
    private List<Comment> Comments = new ArrayList<>();
}