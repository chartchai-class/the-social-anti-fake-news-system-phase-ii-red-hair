package se331.lab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se331.project.entity.News;

public interface NewsRepository extends JpaRepository<News, Long> {
}