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
    public Page<NewsDto> getNews(String status, String searchBy, String search, Pageable pageable) {

        // logic will be in dao
        Page<News> newsPage = newsDao.getNews(status, searchBy, search, pageable);

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
        return newsDao.save(news);
    }

    @Override
    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }

    @Override
    public void updateIsDeleted(Long id, Boolean isDeleted) {
        News news = newsDao.updateIsDeleted(id, isDeleted);
        AMapper.INSTANCE.getNewsDto(news);
    }
}