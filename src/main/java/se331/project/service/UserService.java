package se331.project.service;

import jakarta.transaction.Transactional;
import se331.project.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(Long id);

    List<User> findAllUsers();

    @Transactional
    User findByUsername(String username);

    User findByEmail(String email);

    User save(User user);
}