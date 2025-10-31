package se331.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsDto {
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
    Boolean isDeleted;
//    UserProfileDto reporter; // just temp, can create NewsUserDto and update
    String reporter;
    List<CommentDto> comments;
}