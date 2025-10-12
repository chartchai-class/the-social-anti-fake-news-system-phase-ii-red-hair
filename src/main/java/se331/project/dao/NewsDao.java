package se331.project.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.project.entity.News;

public interface NewsDao {
    Page<News> getNews(Pageable pageRequest);
}