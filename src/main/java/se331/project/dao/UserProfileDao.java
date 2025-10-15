package se331.project.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.project.entity.UserProfile;

import java.util.List;
import java.util.Optional;

public interface UserProfileDao {
    Integer getUserProfileSize();
    Page<UserProfile> getAllUserProfiles(Pageable pageable);
    Optional<UserProfile> findById(Long id);
    UserProfile findByDisplayName(String displayName); //My flaw might be returning only a single unit for displayName search
    UserProfile findByEmail(String email);
    UserProfile save(UserProfile userProfile);
}