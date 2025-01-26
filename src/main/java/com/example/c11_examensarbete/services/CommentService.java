package com.example.c11_examensarbete.services;

import com.example.c11_examensarbete.dtos.CommentDto;
import com.example.c11_examensarbete.entities.Comment;
import com.example.c11_examensarbete.entities.User;
import com.example.c11_examensarbete.exceptionMappers.BadRequestExceptionMapper;
import com.example.c11_examensarbete.exceptionMappers.ResourceNotFoundExceptionMapper;
import com.example.c11_examensarbete.repositories.CommentRepository;
import com.example.c11_examensarbete.repositories.ReviewRepository;
import com.example.c11_examensarbete.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    CommentRepository commentRepository;
    ReviewRepository reviewRepository;
    UserRepository userRepository;
    SecurityService securityService;

    public CommentService(CommentRepository commentRepository, ReviewRepository reviewRepository, UserRepository userRepository, SecurityService securityService) {
        this.commentRepository = commentRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.securityService = securityService;
    }

    public List<CommentDto> getCommentsByReview(int id){
        if (id <= 0) {
            throw new BadRequestExceptionMapper("Review ID must be a positive integer.");
        }
        List<CommentDto> comments = commentRepository.findAll().stream()
                .filter(comment -> comment.getReview().getId() == id)
                .map(CommentDto::fromComment)
                .toList();
        if (comments.isEmpty()) {
            throw new ResourceNotFoundExceptionMapper("No comments found for review with ID " + id);
        }
        return comments;
    }

    public List<CommentDto> getCommentsByUser(String oauthId) {
        if (oauthId == null || oauthId.isEmpty()) {
            throw new BadRequestExceptionMapper("ID must be a valid");
        }

        User user = userRepository.findByOauthProviderId(oauthId)
                .orElseThrow(() -> new ResourceNotFoundExceptionMapper("User not found"));

        securityService.validateUserAcces(user.getOauthProviderId(), oauthId);

        List<CommentDto> comments = commentRepository.findAll().stream()
                .filter(comment -> comment.getUser().getId().equals(user.getId()))
                .map(CommentDto::fromComment)
                .toList();
        if (comments.isEmpty()) {
            throw new ResourceNotFoundExceptionMapper("No comments found for user with ID " + user.getId());
        }
        return comments;
    }

    public int addComment(CommentDto commentDto){
        if (commentDto.reviewId() <= 0) {
            throw new BadRequestExceptionMapper("Review ID must be a positive integer.");
        }
        if (commentDto.userId() == null || commentDto.userId() <= 0) {
            throw new BadRequestExceptionMapper("User ID must be a positive integer and not null.");
        }
        if (commentDto.commentText() == null || commentDto.commentText().trim().isEmpty()) {
            throw new BadRequestExceptionMapper("Comment text must not be null or empty.");
        }
        if (commentDto.commentText().length() > 400) {
            throw new BadRequestExceptionMapper("Comment text must not be longer than 400 characters.");
        }

        Comment comment = new Comment();
        comment.setCommentText(commentDto.commentText());
        comment.setReview(reviewRepository.findById(commentDto.reviewId())
                .orElseThrow(() -> new ResourceNotFoundExceptionMapper("Review with ID " + commentDto.reviewId() + " not found.")));
        comment.setUser(userRepository.findById(commentDto.userId())
                .orElseThrow(() -> new ResourceNotFoundExceptionMapper("User with ID " + commentDto.userId() + " not found.")));
        commentRepository.save(comment);

        return comment.getId();
    }

    public void deleteComment(int id) {
        if (id <= 0) {
            throw new BadRequestExceptionMapper("Comment ID must be a positive integer.");
        }
        if (!commentRepository.existsById(id)) {
            throw new ResourceNotFoundExceptionMapper("Comment with ID " + id + " not found.");
        }
        commentRepository.deleteById(id);
    }
}
