package se331.project.service;

import jakarta.transaction.Transactional;
import se331.project.entity.UserProfile;

import java.util.List;
import java.util.Optional;

public interface UserProfileService {
    List<UserProfile> findAllUserProfiles();
    Optional<UserProfile> findById(Long id);
    UserProfile findByDisplayName(String displayName);
    UserProfile findByEmail(String email);
    UserProfile save(UserProfile userProfile);
}