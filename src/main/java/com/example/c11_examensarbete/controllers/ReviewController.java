package com.example.c11_examensarbete.controllers;

import com.example.c11_examensarbete.services.MangaService;
import com.example.c11_examensarbete.services.ReviewService;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/reviews/user/{userid}")
    public List<ReviewDto> getAllUsersReviews(@PathVariable int userid) {
        return reviewService.getReviewsByUser(userid);
    }

    //TODO: Works in bruno but no exeption handling
    @GetMapping("/reviews/manga/{mangaid}")
    public List<ReviewDto> getReviewsForManga(@PathVariable int mangaid){
       return reviewService.getReviewsByManga(mangaid);
    }

    //TODO: Works in bruno but no exeption handling
    @PostMapping("/reviews")
    public ResponseEntity<Void> addReview(@RequestBody ReviewDto reviewDto){
        int id = reviewService.addReview(reviewDto);
        return ResponseEntity.created(URI.create("/reviews/" + id)).build();
    }
}
