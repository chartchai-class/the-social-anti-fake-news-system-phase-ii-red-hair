package se331.project.dao;

import se331.project.entity.UserProfile;

import java.util.List;
import java.util.Optional;

public interface UserProfileDao {
    Optional<UserProfile> findById(Long id);
    List<UserProfile> findAllUserProfiles();
    UserProfile findByUsername(String username);
    UserProfile findByEmail(String email);
    UserProfile save(UserProfile userProfile);
}