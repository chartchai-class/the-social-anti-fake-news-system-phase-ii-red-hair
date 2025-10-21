package se331.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import se331.project.entity.News;

public interface NewsRepository extends JpaRepository<News, Long> {

    // only status
    Page<News> findByVoteType(String voteType, Pageable pageable);


    // only serch by

    // serch only Title
    Page<News> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    // serch only Description
    Page<News> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);

    // serch only Reporter
    Page<News> findByReporter_User_UsernameContainingIgnoreCase(String username, Pageable pageable);


    //  Double Filter

    // serch  Status AND Title
    Page<News> findByVoteTypeAndTitleContainingIgnoreCase(String voteType, String title, Pageable pageable);

    // serch  Status AND Description
    Page<News> findByVoteTypeAndDescriptionContainingIgnoreCase(String voteType, String description, Pageable pageable);

    // serch Status AND Reporter
    Page<News> findByVoteTypeAndReporter_User_UsernameContainingIgnoreCase(String voteType, String username, Pageable pageable);



}