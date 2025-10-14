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
public class CommentDto {
    Long id;
    String content;
    String voteType;
    LocalDateTime commentDateTime;
    // change auther in enttity into userdto for not have news
    UserProfileDto author;
}