package com.example.c11_examensarbete.services;

import com.example.c11_examensarbete.dtos.ReviewDto;
import com.example.c11_examensarbete.entities.Manga;
import com.example.c11_examensarbete.entities.Review;
import com.example.c11_examensarbete.entities.User;
import com.example.c11_examensarbete.repositories.MangaRepository;
import com.example.c11_examensarbete.repositories.ReviewRepository;
import com.example.c11_examensarbete.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    ReviewRepository reviewRepository;
    MangaRepository mangaRepository;
    UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, MangaRepository mangaRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.mangaRepository = mangaRepository;
        this.userRepository = userRepository;
    }

    public List<ReviewDto> getReviewsByManga(int mangaId) {
        return reviewRepository.findAll().stream()
                .filter(review -> review.getManga().getId() == mangaId)
                .map(ReviewDto::fromReview)
                .toList();
    }

    public List<ReviewDto> getReviewsByUser(int userId) {
        return reviewRepository.findAll().stream()
                .filter(review -> review.getUser().getId() == userId)
                .map(ReviewDto::fromReview)
                .toList();
    }

    public int addReview(ReviewDto reviewDto) {

        if (reviewDto.mangaId() == null) {
            throw new IllegalArgumentException("Manga ID must not be null");
        }
        if (reviewDto.userId() == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }

        Review review = new Review();
        review.setTitle(reviewDto.title());
        review.setReviewText(reviewDto.reviewText());
        review.setSpoiler(reviewDto.spoiler());
        review.setRating(reviewDto.rating());

        // Fetch the Manga entity by ID
        Manga manga = mangaRepository.findById(reviewDto.mangaId())
                .orElseThrow(() -> new IllegalArgumentException("Manga not found"));
        review.setManga(manga);

        // Fetch the User entity by ID
        User user = userRepository.findById(reviewDto.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        review.setUser(user);
        reviewRepository.save(review);
        return review.getId();
    }
}
