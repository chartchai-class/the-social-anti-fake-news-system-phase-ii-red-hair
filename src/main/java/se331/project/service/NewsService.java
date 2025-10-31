package se331.project.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.project.dto.NewsDto;
import se331.project.entity.News;


// this i will use to pull data and send to frontend
public interface NewsService {

    Page<NewsDto> getNews(String status, String searchBy, String search, Pageable pageable);
    NewsDto getNewsById(Long id);
    News save(News news);
    void deleteById(Long id);
    void updateIsDeleted(Long id, Boolean isDeleted);

    Page<NewsDto> getNewsByAdmin(String status, String searchBy, String search, Pageable pageable);
}