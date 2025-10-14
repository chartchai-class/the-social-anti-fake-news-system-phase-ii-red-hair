package se331.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se331.project.dao.UserProfileDao;
import se331.project.entity.UserProfile;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
    final UserProfileDao userProfileDao;

    @Override
    public Optional<UserProfile> findById(Long id){
        return userProfileDao.findById(id);
    }

    @Override
    public List<UserProfile> findAllUserProfiles(){
        return userProfileDao.findAllUserProfiles();
    }

    @Override
    @Transactional
    public UserProfile findByDisplayName(String displayName) {
        return userProfileDao.findByDisplayName(displayName);
    }

    @Override
    @Transactional
    public UserProfile findByEmail(String email) {
        return userProfileDao.findByEmail(email);
    }

    @Override
    @Transactional
    public UserProfile save(UserProfile userProfile) {
        return userProfileDao.save(userProfile);
    }
}