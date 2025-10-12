package se331.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import se331.project.entity.News;

public interface NewsRepository extends JpaRepository<News, Long> {
    //this is use to tell jpa to auto query and filter fake or nofa-ke
    Page<News> findByVoteType(String voteType, Pageable pageable);
    // this use to search form title or description
    Page<News> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description, Pageable pageable);


}