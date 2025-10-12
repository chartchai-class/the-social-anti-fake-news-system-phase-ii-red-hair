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
    public Page<News> getNews(String status, String search, Pageable pageable) {
        // check if it have serch value send or not
        if (search != null && !search.isEmpty()) {
            return newsDao.getNews(search, search, pageable);
        }
        // if dont have serch value check if it have votetype or not
        else if (status != null && !status.isEmpty()) {
            return newsDao.getNews(status, pageable);
        }
        // if no search or votetype it will get all data
        else {
            return newsDao.getNews(pageable);
        }
    }

    @Override
    public News getNewsById(Long id) {

        return newsRepository.findById(id).orElse(null);
    }
}