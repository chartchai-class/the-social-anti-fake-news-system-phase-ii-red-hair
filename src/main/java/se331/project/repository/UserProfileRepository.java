package se331.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se331.project.entity.UserProfile;

import java.util.List;
import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByEmail(String email);
    Optional<UserProfile> findByDisplayName(String displayName);
    List<UserProfile> findAll();
}