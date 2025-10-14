package se331.project.service;

import jakarta.transaction.Transactional;
import se331.project.entity.UserProfile;

import java.util.List;
import java.util.Optional;

public interface UserProfileService {
    Optional<UserProfile> findById(Long id);

    List<UserProfile> findAllUserProfiles();

    @Transactional
    UserProfile findByUsername(String username);

    UserProfile findByEmail(String email);

    UserProfile save(UserProfile userProfile);
}