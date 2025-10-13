package se331.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// only have user data for not having loop
public class UserDto {
    Long id;
    String firstname;
    String lastname;
}