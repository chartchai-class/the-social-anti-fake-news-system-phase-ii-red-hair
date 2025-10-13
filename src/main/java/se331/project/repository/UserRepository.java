package se331.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se331.project.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    List<User> findAll();
}