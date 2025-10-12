package se331.project.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.project.entity.News;


// this i will use to pull data and send to frontend
public interface NewsService {
    Page<News> getNews(String status, String search, Pageable pageable);
    News getNewsById(Long id);
}