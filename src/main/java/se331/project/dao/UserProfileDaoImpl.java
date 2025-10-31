package se331.project.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Integer getUserProfileSize() { return Math.toIntExact(userRepository.count()); }

    @Override
    public Optional<UserProfile> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Page<UserProfile> getAllUserProfiles(Pageable pageable) { return userRepository.findAll(pageable); }

    @Override
    public UserProfile findByDisplayName(String displayName) { return userRepository.findByDisplayName(displayName).orElse(null); }

    @Override
    public UserProfile findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public UserProfile save(UserProfile userProfile) {
        return userRepository.save(userProfile);
    }

}