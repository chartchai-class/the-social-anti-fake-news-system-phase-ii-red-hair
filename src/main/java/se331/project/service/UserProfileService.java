package se331.project.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.project.entity.UserProfile;

import java.util.List;
import java.util.Optional;

public interface UserProfileService {
    Integer getUserProfileSize();
    Page<UserProfile> getAllUserProfiles(Pageable pageable);
    Optional<UserProfile> findById(Long id);
    UserProfile findByDisplayName(String displayName);
    UserProfile findByEmail(String email);
    UserProfile save(UserProfile userProfile);
}