package se331.project.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se331.project.dto.CommentDto;
import se331.project.entity.Comment;
import se331.project.service.CommentService;

@RestController
@RequiredArgsConstructor
public class CommentController {

    final CommentService commentService;

    //  IMPORTANT will delete later *************
    // about authorId  is  it the same as User Id when login ,so we need to change it later when we finish security part
    @PostMapping("/news/{newsId}/comments")
    //this is api for create new comment
    public ResponseEntity<?> createComment(
            @PathVariable("newsId") Long newsId,
            @RequestParam("authorId") Long authorId,
            @RequestBody Comment comment
    ) {

        //call method save form commetservice that i wirte
        CommentDto newComment = commentService.save(newsId, authorId, comment);

        //when service success this will send data newcommet back to frontend
        return ResponseEntity.ok(newComment);
    }

    // this api use for delete comment
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long id) {
        // call method
        commentService.deleteById(id);

        //204
        return ResponseEntity.noContent().build();
    }
}