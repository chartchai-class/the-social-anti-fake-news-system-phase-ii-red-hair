package se331.project.dao;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import se331.project.entity.News;
import se331.project.repository.NewsRepository;

@Repository
@RequiredArgsConstructor
public class NewsDaoImpl implements NewsDao {
    final NewsRepository newsRepository;

    @Override
    public Page<News> getNews(Pageable pageRequest) {
        return newsRepository.findByIsDeletedFalse(pageRequest);
    }
    // this part so hard i use help form other
    // this is method for implement logic all
    @Override
    public Page<News> getNews(String status, String searchBy, String search, Pageable pageable) {

        // check filter 'status' or not
        boolean hasStatusFilter = status != null && !status.isEmpty() && !status.equalsIgnoreCase("all");

        // check filter 'search' or not
        boolean hasSearchFilter = searchBy != null && !searchBy.isEmpty() && search != null && !search.isEmpty();

        // logic for choose repository method

        // Case 1 Double Filter
        if (hasStatusFilter && hasSearchFilter) {
            switch (searchBy.toLowerCase()) {
                case "title":
                    return newsRepository.findByVoteTypeAndTitleContainingIgnoreCaseAndIsDeletedFalse(status, search, pageable);
                case "description":
                    return newsRepository.findByVoteTypeAndDescriptionContainingIgnoreCaseAndIsDeletedFalse(status, search, pageable);
                case "reporter":
                    return newsRepository.findByVoteTypeAndReporter_DisplayNameContainingIgnoreCaseAndIsDeletedFalse(status, search, pageable);
                default:
                    return newsRepository.findByVoteTypeAndIsDeletedFalse(status, pageable);
            }
        }

        // Case 2 filter SearchBy
        else if (hasSearchFilter) {
            switch (searchBy.toLowerCase()) {
                case "title":
                    return newsRepository.findByTitleContainingIgnoreCaseAndIsDeletedFalse(search, pageable);
                case "description":
                    return newsRepository.findByDescriptionContainingIgnoreCaseAndIsDeletedFalse(search, pageable);
                case "reporter":
                    return newsRepository.findByReporter_DisplayNameContainingIgnoreCaseAndIsDeletedFalse(search, pageable);
                default:
                    return newsRepository.findAll(pageable);
            }
        }

        // Case 3: only Status
        else if (hasStatusFilter) {
            return newsRepository.findByVoteTypeAndIsDeletedFalse(status, pageable);
        }

        // Case 4: no filter
        else {
            return newsRepository.findByIsDeletedFalse(pageable);
        }
    }

    @Override
    public News save(News news) {
        return newsRepository.save(news);
    }

    @Override
    public News updateIsDeleted(Long id, Boolean isDeleted) {
        News news = newsRepository.findById(id).orElse(null);

        if(news==null){
            throw new EntityNotFoundException("News not found");
        }
        if(isDeleted == null){
            throw new IllegalArgumentException("isDeleted must not be null");
        }

        if (news.getIsDeleted() == isDeleted) { return news; } //do nth

        news.setIsDeleted(isDeleted);
        return newsRepository.save(news);
    }


    // for admin -- I duplicated your old code bro
    @Override
    public Page<News> getNewsByAdmin(Pageable pageRequest) {
        return newsRepository.findAll(pageRequest);
    }

    @Override
    public Page<News> getNewsByAdmin(String status, String searchBy, String search, Pageable pageable) {

        // check filter 'status' or not
        boolean hasStatusFilter = status != null && !status.isEmpty() && !status.equalsIgnoreCase("all");

        // check filter 'search' or not
        boolean hasSearchFilter = searchBy != null && !searchBy.isEmpty() && search != null && !search.isEmpty();

        // logic for choose repository method
        // Case 1 Double Filter
        if (hasStatusFilter && hasSearchFilter) {
            switch (searchBy.toLowerCase()) {
                case "title":
                    return newsRepository.findByVoteTypeAndTitleContainingIgnoreCase(status, search, pageable);
                case "description":
                    return newsRepository.findByVoteTypeAndDescriptionContainingIgnoreCase(status, search, pageable);
                case "reporter":
                    return newsRepository.findByVoteTypeAndReporter_DisplayNameContainingIgnoreCase(status, search, pageable);
                default:
                    return newsRepository.findByVoteType(status, pageable);
            }
        }

        // Case 2 filter SearchBy
        else if (hasSearchFilter) {
            switch (searchBy.toLowerCase()) {
                case "title":
                    return newsRepository.findByTitleContainingIgnoreCase(search, pageable);
                case "description":
                    return newsRepository.findByDescriptionContainingIgnoreCase(search, pageable);
                case "reporter":
                    return newsRepository.findByReporter_DisplayNameContainingIgnoreCase(search, pageable);
                default:
                    return newsRepository.findAll(pageable);
            }
        }

        // Case 3: only Status
        else if (hasStatusFilter) {
            return newsRepository.findByVoteType(status, pageable);
        }

        // Case 4: no filter
        else {
            return newsRepository.findAll(pageable);
        }
    }


}