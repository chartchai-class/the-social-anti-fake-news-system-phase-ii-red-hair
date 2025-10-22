package se331.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/comments/{id}/toggle-delete")
    public ResponseEntity<?> toggleDeleteComment(@PathVariable("id") Long id, @RequestBody Comment comment) {
        if (comment.getIsDeleted() == null) {return ResponseEntity.badRequest().build();}
        Boolean commentIsDeleted = comment.getIsDeleted();
        commentService.updateIsDeleted(id, commentIsDeleted);
        return ResponseEntity.noContent().build();
    }

    /*
     Bro, I can't filter out the visibility of comments by role if comments are fetched by child of news controller, so I seperatedly created it.
     Sorry for writing too many lines of code on your part.
     */

    // non-admin get only visible comments
    @GetMapping("/news/{id}/comments")
    public ResponseEntity<?> getCommentsByNewsId(@PathVariable("id") Long newsId, Pageable pageRequest) {
        Page<CommentDto> pageOutput = commentService.getCommentsByNewsId(newsId, pageRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add("x-total-count", String.valueOf(pageOutput.getTotalElements()));

        return new ResponseEntity<>(pageOutput.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/admin/news/{id}/comments")
    public ResponseEntity<?> getCommentsByNewsIdByAdmin(@PathVariable("id") Long id, Pageable pageRequest) {
        Page<CommentDto> pageOutput = commentService.getCommentsByNewsIdByAdmin(id, pageRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add("x-total-count", String.valueOf(pageOutput.getTotalElements()));

        return new ResponseEntity<>(pageOutput.getContent(), headers, HttpStatus.OK);
    }
}