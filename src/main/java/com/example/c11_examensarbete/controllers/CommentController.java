package com.example.c11_examensarbete.controllers;

import com.example.c11_examensarbete.dtos.CommentDto;
import com.example.c11_examensarbete.services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/review/")
public class CommentController {

    CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    //TODO: works in bruno but no exception handling
    @GetMapping("/comments/{reviewid}")
    public ResponseEntity<List<CommentDto>> getCommentsByReview(@PathVariable int reviewid) {
        return ResponseEntity.ok(commentService.getCommentsByReview(reviewid));
    }

    //TODO: works in bruno but no exception handling
    @GetMapping("/comments/user")
    public ResponseEntity<List<CommentDto>> getCommentsByUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String oauthId = auth.getName();
        return ResponseEntity.ok(commentService.getCommentsByUser(oauthId));
    }

    //TODO: Works in bruno but no exeption handling
    @PostMapping("/comment")
    public ResponseEntity<Void> addComment(@RequestBody CommentDto commentDto) {
        int id = commentService.addComment(commentDto);
        return ResponseEntity.created(URI.create("/reviews/comments/" + id)).build();
    }

    //TODO: works in bruno but no exception handling
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable int id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
