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
    Page<News> findByContentContainingIgnoreCase(String description, Pageable pageable);

    // serch only Reporter
    Page<News> findByReporter_DisplayNameContainingIgnoreCase(String username, Pageable pageable);


    //  Double Filter

    // serch  Status AND Title
    Page<News> findByVoteTypeAndTitleContainingIgnoreCase(String voteType, String title, Pageable pageable);

    // serch  Status AND Description
    Page<News> findByVoteTypeAndContentContainingIgnoreCase(String voteType, String description, Pageable pageable);

    // serch Status AND Reporter
    Page<News> findByVoteTypeAndReporter_DisplayNameContainingIgnoreCase(String voteType, String username, Pageable pageable);



    // Messy code starts here
    // Return everything above with IsDeletedFalse
    //findall
    Page<News> findByIsDeletedFalse(Pageable pageable);

    // only status
    Page<News> findByVoteTypeAndIsDeletedFalse(String voteType, Pageable pageable);

    // only search by
    // only Title
    Page<News> findByTitleContainingIgnoreCaseAndIsDeletedFalse(String title, Pageable pageable);
    // only Description
    Page<News> findByContentContainingIgnoreCaseAndIsDeletedFalse(String description, Pageable pageable);
    // only Reporter
    Page<News> findByReporter_DisplayNameContainingIgnoreCaseAndIsDeletedFalse(String username, Pageable pageable);

    //  Double Filter
    // search  Status AND Title
    Page<News> findByVoteTypeAndTitleContainingIgnoreCaseAndIsDeletedFalse(String voteType, String title, Pageable pageable);
    // search  Status AND Description
    Page<News> findByVoteTypeAndContentContainingIgnoreCaseAndIsDeletedFalse(String voteType, String description, Pageable pageable);
    // search Status AND Reporter
    Page<News> findByVoteTypeAndReporter_DisplayNameContainingIgnoreCaseAndIsDeletedFalse(String voteType, String username, Pageable pageable);


}