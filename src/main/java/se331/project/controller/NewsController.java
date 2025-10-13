package se331.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se331.project.dto.NewsDto;
import se331.project.entity.News;
import se331.project.service.NewsService;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class NewsController {
    final NewsService newsService;

    // use to pull data in pagination and can filrer or serach
    @GetMapping("/news")
    public ResponseEntity<?> getNews(
            // use to make spring pull query paramiter  url in to status
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "search", required = false) String search,
            Pageable pageable // this is so op it automaticly collect pagination and sorting parameters for me
    ) {

        Page<NewsDto> pageOutput = newsService.getNews(status, search, pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.add("x-total-count", String.valueOf(pageOutput.getTotalElements()));

        return new ResponseEntity<>(pageOutput.getContent(), headers, HttpStatus.OK);
    }

    // use to pull one news by ID
    @GetMapping("/news/{id}")
    public ResponseEntity<?> getNewsById(@PathVariable("id") Long id) {
        NewsDto news = newsService.getNewsById(id);

        if (news == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "News not found with id: " + id);
        }

        return ResponseEntity.ok(news);
    }

    //this is api for add news (User)
    @PostMapping("/news")
    public ResponseEntity<?> createNews(@RequestBody News news) {
        // can use to set time before save news
        news.setNewsDateTime(LocalDateTime.now());
        News newNews = newsService.save(news);
        return ResponseEntity.ok(newNews);
    }

    // this api use to delete news (Admin)
    @DeleteMapping("/news/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable("id") Long id) {
        newsService.deleteById(id);
        // self remider for me that when successful delete it will normal show 204 ,
        return ResponseEntity.noContent().build();
    }
}