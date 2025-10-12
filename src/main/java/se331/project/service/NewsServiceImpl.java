package se331.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import se331.project.dao.NewsDao;
import se331.project.entity.News;
import se331.project.repository.NewsRepository;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    final NewsDao newsDao;
    final NewsRepository newsRepository;

    @Override
    public Page<News> getNews(Pageable pageRequest) {
        return newsDao.getNews(pageRequest);
    }

    @Override
    public News getNewsById(Long id) {
        // if not found it will return null
        return newsRepository.findById(id).orElse(null);
    }
}