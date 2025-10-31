package se331.project.dto;

import jakarta.persistence.OneToOne;
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
    String displayName;
    String username; // Dont reference this for other Dto
    String email;
    String profileImage;
    String phoneNumber;
    List<UserProfileNewsDto> reportedNews;
    List<UserProfileCommentDto> comments;
    List<Role> roles;
}

// intentionally left parentId, password, enabled, tokens here. I think they are kind of sensitive data that dont need to be shared.