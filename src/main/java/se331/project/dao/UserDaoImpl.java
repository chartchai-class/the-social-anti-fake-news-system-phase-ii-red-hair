package se331.project.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se331.project.entity.User;
import se331.project.repository.UserRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    final UserRepository userRepository;

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}