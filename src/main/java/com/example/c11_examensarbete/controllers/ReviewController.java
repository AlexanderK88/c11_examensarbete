package com.example.c11_examensarbete.controllers;

import com.example.c11_examensarbete.services.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.c11_examensarbete.dtos.ReviewDto;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ReviewController {
    ReviewService reviewService;;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    //TODO: Works in bruno but no exeption handling
    @GetMapping("/reviews/user")
    public ResponseEntity<List<ReviewDto>> getAllUsersReviews() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String oauthId = auth.getName();
        return ResponseEntity.ok(reviewService.getReviewsByUser(oauthId));
    }

    //TODO: Works in bruno but no exeption handling
    @GetMapping("/reviews/manga/{mangaid}")
    public ResponseEntity<List<ReviewDto>> getReviewsForManga(@PathVariable int mangaid){
       return ResponseEntity.ok(reviewService.getReviewsByManga(mangaid));
    }

    //TODO: Works in bruno but no exeption handling
    @PostMapping("/reviews")
    public ResponseEntity<Void> addReview(@RequestBody ReviewDto reviewDto){
        int id = reviewService.addReview(reviewDto);
        return ResponseEntity.created(URI.create("/reviews/" + id)).build();
    }

    @DeleteMapping("/review/delete/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable int reviewId){
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}
