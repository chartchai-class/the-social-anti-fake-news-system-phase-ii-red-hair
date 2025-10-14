package se331.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se331.project.security.user.Role;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {
    Long id;
    String firstName;
    String lastName;
    String username;
    String email;
    String profileImage;
    List<UserProfileNewsDto> reportedNews;
    List<UserProfileCommentDto> comments;
}

// intentionally left parentId, password, enabled, tokens here. I think they are kind of sensitive data that dont need to be shared.