package se331.project.dao;

import se331.project.entity.User;
import java.util.Optional;

public interface UserDao {
    Optional<User> findById(Long id);
    User findByUsername(String username);
    User save(User user);
}