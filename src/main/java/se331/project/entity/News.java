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

    @Column (length = 500)
    private String description;

    @Column(length = 10000)
    private String content;

    private String image; //  url for image
    private Integer fakeCount;
    private Integer notFakeCount;
    private String voteType;
    @Builder.Default
    private Boolean isDeleted=Boolean.FALSE;

    // new can oly by one user
    @ManyToOne
    private UserProfile reporter;

    // new have many comment
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();
}