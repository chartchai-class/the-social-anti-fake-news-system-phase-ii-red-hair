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
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class NewsController {
    final NewsService newsService;

    // use to pull data in pagination and can filrer or serach
    // this will be used by non-admin
    @GetMapping("/news")
    public ResponseEntity<?> getNews(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "searchBy", required = false) String searchBy, // <-- เพิ่มตัวนี้
            @RequestParam(value = "search", required = false) String search,
            Pageable pageable
    ) {

        // send parmiter to service
        Page<NewsDto> pageOutput = newsService.getNews(status, searchBy, search, pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.add("x-total-count", String.valueOf(pageOutput.getTotalElements()));

        return new ResponseEntity<>(pageOutput.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/admin/news")
    public ResponseEntity<?> getNewsByAdmin(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "searchBy", required = false) String searchBy, // <-- เพิ่มตัวนี้
            @RequestParam(value = "search", required = false) String search,
            Pageable pageable
    ) {

        // send parmiter to service
        Page<NewsDto> pageOutput = newsService.getNewsByAdmin(status, searchBy, search, pageable);

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

    // to soft-delete toggle
    @PostMapping("/news/{id}/toggle-delete")
    public ResponseEntity<?> toggleSoftDeleteNews(@PathVariable Long id, @RequestBody News news) {
        if (news.getIsDeleted() == null) {
            return ResponseEntity.badRequest().build();
        }
        Boolean newsIsDeleted = news.getIsDeleted();
        newsService.updateIsDeleted(id, newsIsDeleted);
        return ResponseEntity.noContent().build();
    }
}