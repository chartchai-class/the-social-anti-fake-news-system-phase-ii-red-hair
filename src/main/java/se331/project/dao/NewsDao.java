package se331.project.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.project.entity.News;

public interface NewsDao {
    Page<News> getNews(Pageable pageRequest);

    // new method for get all parameters
    Page<News> getNews(String status, String searchBy, String search, Pageable pageable);

    News save(News news);

    News updateIsDeleted(Long id, Boolean isDeleted);

}