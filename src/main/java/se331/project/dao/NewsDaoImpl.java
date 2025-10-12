package se331.project.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import se331.project.entity.News;
import se331.project.repository.NewsRepository;

@Repository
@RequiredArgsConstructor
public class NewsDaoImpl implements NewsDao {
    final NewsRepository newsRepository;

    @Override
    public Page<News> getNews(Pageable pageRequest) {
        return newsRepository.findAll(pageRequest);
    }

    // method Filter
    @Override
    public Page<News> getNews(String status, Pageable pageable) {
        return newsRepository.findByVoteType(status, pageable);
    }

    // mentos for Search
    @Override
    public Page<News> getNews(String title, String description, Pageable pageable) {
        return newsRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(title, description, pageable);
    }

}