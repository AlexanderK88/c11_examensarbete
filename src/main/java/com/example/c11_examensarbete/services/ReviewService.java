package com.example.c11_examensarbete.services;

import com.example.c11_examensarbete.dtos.ReviewDto;
import com.example.c11_examensarbete.entities.Manga;
import com.example.c11_examensarbete.entities.Review;
import com.example.c11_examensarbete.entities.User;
import com.example.c11_examensarbete.exceptionMappers.AccessDeniedExceptionMapper;
import com.example.c11_examensarbete.exceptionMappers.BadRequestExceptionMapper;
import com.example.c11_examensarbete.exceptionMappers.ResourceNotFoundExceptionMapper;
import com.example.c11_examensarbete.repositories.MangaRepository;
import com.example.c11_examensarbete.repositories.ReviewRepository;
import com.example.c11_examensarbete.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {

    ReviewRepository reviewRepository;
    MangaRepository mangaRepository;
    UserRepository userRepository;
    SecurityService securityService;

    public ReviewService(ReviewRepository reviewRepository, MangaRepository mangaRepository, UserRepository userRepository, SecurityService securityService) {
        this.reviewRepository = reviewRepository;
        this.mangaRepository = mangaRepository;
        this.userRepository = userRepository;
        this.securityService = securityService;
    }

    public List<ReviewDto> getReviewsByManga(int mangaId) {
        if(mangaId <= 0 ){
            throw new BadRequestExceptionMapper("mangaId can not be less than or equals to zero");
        }
        return reviewRepository.findAll().stream()
                .filter(review -> review.getManga().getId() == mangaId)
                .map(ReviewDto::fromReview)
                .toList();
    }

    public List<ReviewDto> getReviewsByUser(String oauthId) {
        if(oauthId == null || oauthId.isEmpty() ){
            throw new BadRequestExceptionMapper("userId can not be less than or equals to zero");
        }

        User user = userRepository.findByOauthProviderId(oauthId)
                .orElseThrow(() -> new ResourceNotFoundExceptionMapper("userId not found"));

        securityService.validateUserAcces(user.getOauthProviderId(), user.getUsername());

        return reviewRepository.findAll().stream()
                .filter(review -> review.getUser().getId().equals(user.getId()))
                .map(ReviewDto::fromReview)
                .toList();
    }

    public int addReview(ReviewDto reviewDto) {
        if(reviewDto == null) {
            throw new BadRequestExceptionMapper("reviewDto can not be null");
        }

        if (reviewDto.mangaId() == null) {
            throw new BadRequestExceptionMapper("Manga ID must not be null");
        }
        if (reviewDto.userId() == null) {
            throw new BadRequestExceptionMapper("User ID must not be null");
        }

        Review review = new Review();
        review.setReviewText(reviewDto.reviewText());
        review.setSpoiler(reviewDto.spoiler());
        review.setRating(reviewDto.rating());

        // Fetch the Manga entity by ID
        Manga manga = mangaRepository.findById(reviewDto.mangaId())
                .orElseThrow(() -> new ResourceNotFoundExceptionMapper("Manga not found"));
        review.setManga(manga);

        // Fetch the User entity by ID
        User user = userRepository.findById(reviewDto.userId())
                .orElseThrow(() -> new ResourceNotFoundExceptionMapper("User not found"));
        review.setUser(user);
        reviewRepository.save(review);
        return review.getId();
    }

    @Transactional
    public void deleteReview(int reviewId) {
        if(reviewId <= 0 ) {
            throw new BadRequestExceptionMapper("reviewId can not be less than 0");
        }
        reviewRepository.findById(reviewId)
                        .orElseThrow(() -> new ResourceNotFoundExceptionMapper("review not found"));
        reviewRepository.deleteById(reviewId);
    }
}
