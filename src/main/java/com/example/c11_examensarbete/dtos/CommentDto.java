package com.example.c11_examensarbete.dtos;

import com.example.c11_examensarbete.entities.Comment;

public record CommentDto(
        String commentText,
        Integer reviewId,
        Integer userId
) {
    public static CommentDto fromComment(Comment comment) {
        return new CommentDto(
                comment.getCommentText(),
                comment.getReview().getId(),
                comment.getUser().getId()
        );
    }
}
