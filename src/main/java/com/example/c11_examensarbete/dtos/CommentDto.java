package com.example.c11_examensarbete.dtos;

import com.example.c11_examensarbete.entities.Comment;

import java.time.Instant;

public record CommentDto(
        int id,
        String commentText,
        Integer reviewId,
        String username,
        Integer userId,
        Instant createdAt
) {
    public static CommentDto fromComment(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getCommentText(),
                comment.getReview().getId(),
                comment.getUser().getUsername(),
                comment.getUser().getId(),
                comment.getTimestamp()
        );
    }
}
