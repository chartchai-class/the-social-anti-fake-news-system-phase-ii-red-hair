package se331.project.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se331.project.entity.UserProfile;
import se331.project.repository.UserProfileRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserProfileDaoImpl implements UserProfileDao {
    final UserProfileRepository userRepository;

    @Override
    public Optional<UserProfile> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<UserProfile> findAllUserProfiles() {
        return userRepository.findAll();
    }

    @Override
    public UserProfile findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public UserProfile findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public UserProfile save(UserProfile userProfile) {
        return userRepository.save(userProfile);
    }

}