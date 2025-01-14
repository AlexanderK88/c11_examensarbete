package com.example.c11_examensarbete.services;

import com.example.c11_examensarbete.dtos.CommentDto;
import com.example.c11_examensarbete.entities.Comment;
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

    public CommentService(CommentRepository commentRepository, ReviewRepository reviewRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    public List<CommentDto> getCommentsByReview(int id){
        return commentRepository.findAll().stream()
                .filter(comment -> comment.getReview().getId() == id)
                .map(CommentDto::fromComment)
                .toList();
    }

    public List<CommentDto> getCommentsByUser(int id){
        return commentRepository.findAll().stream()
                .filter(comment -> comment.getUser().getId() == id)
                .map(CommentDto::fromComment)
                .toList();
    }

    public int addComment(CommentDto commentDto){
        if(commentDto.reviewId() == 0){
            throw new IllegalArgumentException("Review ID must not be 0");
        }
        if(commentDto.userId() == null){
            throw new IllegalArgumentException("User ID must not be null");
        }
        if(commentDto.commentText() == null){
            throw new IllegalArgumentException("Comment text must not be null");
        }
        if(commentDto.commentText().length() > 400){
            throw new IllegalArgumentException("Comment text must not be longer than 500 characters");
        }
        Comment comment = new Comment();
        comment.setCommentText(commentDto.commentText());
        comment.setReview(reviewRepository.findById(commentDto.reviewId())
                .orElseThrow(() -> new IllegalArgumentException("Review not found")));
        comment.setUser(userRepository.findById(commentDto.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found")));
        commentRepository.save(comment);

        return comment.getId();
    }

    //Works
    public void deleteComment(int id){
        commentRepository.deleteById(id);
    }
}
