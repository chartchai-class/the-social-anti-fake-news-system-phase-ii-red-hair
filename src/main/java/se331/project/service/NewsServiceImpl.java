package se331.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import se331.project.dao.NewsDao;
import se331.project.dto.NewsDto;
import se331.project.entity.News;
import se331.project.repository.NewsRepository;
import se331.project.util.AMapper;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    final NewsDao newsDao;
    final NewsRepository newsRepository;

    @Override
    public Page<NewsDto> getNews(String status, String search, Pageable pageable) {
        // check if it have serch value send or not
        Page<News> newsPage;

        if (search != null && !search.isEmpty()) {
            newsPage = newsDao.getNews(search, search, pageable);
        }

        // if dont have serch value check if it have votetype or not
        else if (status != null && !status.isEmpty()) {
            newsPage = newsDao.getNews(status, pageable);
        }

        // if no search or votetype it will get all data
        else {
            newsPage = newsDao.getNews(pageable);
        }

        //convert data with mapstruct before sending it back
        return newsPage.map(AMapper.INSTANCE::getNewsDto);
    }

    @Override
    public NewsDto getNewsById(Long id) {
        News news = newsRepository.findById(id).orElse(null);
        return AMapper.INSTANCE.getNewsDto(news);
    }

    @Override
    public News save(News news) {
        // can add security here for user role
        return newsRepository.save(news);
    }

    @Override
    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }
}