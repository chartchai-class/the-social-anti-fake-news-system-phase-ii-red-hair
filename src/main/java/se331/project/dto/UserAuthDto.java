package se331.project.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se331.project.entity.Role;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthDto {
    Long id;
    String username;
    String firstName;
    String lastName;
    String email;
    String profileImage;
    List<Role> roles;
}
