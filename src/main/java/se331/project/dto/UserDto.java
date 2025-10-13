package se331.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se331.project.entity.Comment;
import se331.project.entity.News;
import se331.project.entity.Role;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    Long id;
    String firstName;
    String lastName;
    String username;
    String email;
    String profileImage;
    List<UserNewsDto> reportedNews;
    List<Comment> comments;
    List<Role> roles;
}

// intentionally left parentId, password, enabled, tokens here. I think they are kind of sensitive data that dont need to be shared.