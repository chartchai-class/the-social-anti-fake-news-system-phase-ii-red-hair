package se331.project.dao;

import se331.project.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findById(Long id);
    List<User> findAllUsers();
    User findByUsername(String username);
    User findByEmail(String email);
    User save(User user);
}