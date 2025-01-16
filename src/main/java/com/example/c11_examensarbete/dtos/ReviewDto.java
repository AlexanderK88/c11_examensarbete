package com.example.c11_examensarbete.dtos;

import com.example.c11_examensarbete.entities.Review;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record ReviewDto(
        Integer reviewId,
        String title,
        String reviewText,
        boolean spoiler,
        BigDecimal rating,
        Integer userId,
        Integer mangaId,
        List<CommentDto> comments,
        String username,
        Instant createdAt
        ) {
    public static ReviewDto fromReview(Review review) {
        return new ReviewDto(
                review.getId(),
                review.getTitle(),
                review.getReviewText(),
                review.getSpoiler(),
                review.getRating(),
                review.getUser().getId(),
                review.getManga().getId(),
                review.getComments().stream().map(CommentDto::fromComment).toList(),
                review.getUser().getUsername(),
                review.getTimestamp()
        );
    }
}
