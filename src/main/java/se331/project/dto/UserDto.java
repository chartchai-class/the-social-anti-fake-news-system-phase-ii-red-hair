package se331.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se331.project.entity.Role;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// only have user data for not having loop
public class UserDto {
    Long id;
    String firstname;
    String lastname;
    String email;
    String profileImage;
    List<Role> roles;
}