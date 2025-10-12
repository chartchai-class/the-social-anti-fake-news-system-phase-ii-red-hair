package se331.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se331.project.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}