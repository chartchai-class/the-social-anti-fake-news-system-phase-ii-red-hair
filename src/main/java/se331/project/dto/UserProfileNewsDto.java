package se331.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileNewsDto {
    Long id;
    String title;
    String category;
    LocalDateTime newsDateTime;
    String description;
    String content;
    String image;
    Integer fakeCount;
    Integer notFakeCount;
    String voteType;

    NewsDto news;   //maybe update to NewsUserDto later
    CommentDto comment; //CommentUserDto
}
