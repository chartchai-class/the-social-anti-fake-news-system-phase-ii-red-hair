package se331.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se331.project.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<se331.project.security.user.User> findByEmail(String email);

    Optional<se331.project.security.user.User> findByUsername(String username);
}